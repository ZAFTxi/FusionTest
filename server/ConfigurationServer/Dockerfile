# 基础镜像
FROM centos:centos7.9.2009
# 配置环境变量和jdk的安装目录
ENV JAVA_DIR=/usr/local
# 安装jdk

RUN cd $JAVA_DIR \
	# 使用tar命令将当前目录下（这里因为上面配置了所以所在的目录也就是 /usr/local/下面）的jdk文件进行解压
	&& tar -xvf ./jdk-8u321-linux-x64.tar.gz \
	# 然后修改解压后的文件名为java8 ，此时就是 /usr/local/java8

	&& mv ./jdk1.8.0_321 ./java8

# 配置JAVA的环境变量
ENV JAVA_HOME=$JAVA_DIR/java8
# 配置到PAHT中
ENV PATH=$PATH:$JAVA_HOME/bin
# 暴露端口 5566 --！！！容器内部端口
EXPOSE 5566

# 入口，jar文件的启动命令
ENTRYPOINT ['java','-jar','/tmp/server.jar']
