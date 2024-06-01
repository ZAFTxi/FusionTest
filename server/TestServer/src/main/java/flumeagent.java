import org.apache.log4j.pattern.LogEvent;

import java.net.InetSocketAddress;

@Plugin(name = "Flume", category = "Core", elementType = "appender", printObject = true)
public class FlumeAppender extends AbstractAppender {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final FlumeClient client;
    private final String sourceType;

    protected FlumeAppender(String name, Filter filter, Layout<? extends Serializable> layout,
                            FlumeClient client, String sourceType) {
        super(name, filter, layout, true);
        this.client = client;
        this.sourceType = sourceType;
    }

    @PluginFactory
    public static FlumeAppender createAppender(@PluginAttr("name") String name,
                                               @PluginElement("Filters") Filter filter,
                                               @PluginElement("Layout") Layout<? extends Serializable> layout,
                                               @PluginAttr("sourceType") String sourceType,
                                               @PluginAttr("hosts") String hosts) {
        if (name == null) {
            LOGGER.error("FlumeAppender missing name");
            return null;
        }
        if (client == null) {
            LOGGER.error("FlumeAppender missing client");
            return null;
        }
        return new FlumeAppender(name, filter, layout, createClient(hosts), sourceType);
    }

    private static FlumeClient createClient(String hosts) {
        LoadBalancingRpcClient rpcClient = new LoadBalancingRpcClient();
        String[] hostArray = hosts.split(",");
        for (String host : hostArray) {
            String[] hostParts = host.split(":");
            rpcClient.addHost(new InetSocketAddress(hostParts[0], Integer.parseInt(hostParts[1])));
        }
        Properties props = new Properties();
        props.setProperty(RpcClientConfigurationConstants.CONFIG_CLIENT_TYPE, "default_loadbalance");
        props.setProperty(RpcClientConfigurationConstants.CONFIG_HOSTS, hosts);
        props.setProperty(RpcClientConfigurationConstants.CONFIG_MAX_BACKOFF, "10000");
        AvroEventSerializer serializer = new AvroEventSerializer();
        serializer.configure(props, false);
        return new FlumeClient(rpcClient, serializer);
    }

    @Override
    public void append(LogEvent event) {
        try {
            byte[] body = ((StringLayout) this.getLayout()).toByteArray(event);
            Map<String, String> headers = new HashMap<>();
            headers.put("timestamp", Long.toString(event.getTimeMillis()));
            headers.put("source", "log4j");
            headers.put("sourceType", sourceType);
            Event flumeEvent = EventBuilder.withBody(body, headers);
            client.sendEvent(flumeEvent);
        } catch (Exception e) {
            LOGGER.error("Failed to send event to Flume", e);
        }
    }
}