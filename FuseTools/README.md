
## Fusion Error Examples

We show the following fusion error examples. The left window of each pair shows an accident caused by the fusion component and the right window shows the accident would be avoided if the fusion method is replaced to "best-sensor fusion"(defined in the paper) during the pre-crash period in a counter-factual world.


1.OpenPilot (with DEFAULT - the default fusion method as the initial fusion method):
The ego car avoids its collision with the green vehicle ahead after the replacement.
<img src="gif_demos/nsga2_op_446_merge.gif" width="100%" height="100%"/>


2.OpenPilot (with DEFAULT - the default fusion method as the initial fusion method):
The ego car avoids its collision with the green vehicle moving out after the replacement.
<img src="gif_demos/random_op_44_merge.gif" width="100%" height="100%"/>


3.OpenPilot (with MATHWORKS - a kalman-filter based fusion method as the original fusion method):
The ego car avoids its collision with the police car cutting in from the right lane after the replacement.
<img src="gif_demos/random_29_mathwork.gif" width="100%" height="100%"/>


4.OpenPilot (with MATHWORKS - a kalman-filter based fusion method as the original fusion method):
The ego car avoids its collision with the red vehicle cutting in from the right lane after the replacement.
<img src="gif_demos/259_mathwork.gif" width="100%" height="100%"/>


## Getting Started

### Requirements
* Monitor (i.e., due to the limitation of OpenPilot, the simulation can only run on a machine with a monitor/virtual monitor)
* OS: Ubuntu 20.04
* CPU: at least 6 cores
* GPU: at least 6GB memory
* Openpilot 0.8.5 (customized)
* Carla 0.9.11


### Directory Structure
~(home folder)
```
├── openpilot
├── Documents
│   ├── self-driving-cars (created by the user manually)
│   │   ├── FusED
│   │   ├── carla_0911_rss
```
