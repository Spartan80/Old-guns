# Want to create an addon?

## What do you need?

.- You need very basic java knowledge

## How can i start?

.- 1 You need to setup a basic mod

.- 2 Add to build.gradle

![Step 1](https://i.imgur.com/gvvq1AJ.jpg)
![Step 2](https://i.imgur.com/BP7YZSL.jpg)

.- 3 Import it as any mod to your IDE
.- 4 Look at the example mod

## How can i animate the guns?
### You can animate the guns with commands
Type #Help for a list with the commands and the usage

save     - Saves animations on a file with the given name - Usage save,filename.txt

print    - Prints the current animations - Usage print,0

sk       - Starts a keyframe with specified duration - Usage sk,duration

rk       - Resets a keyframe with specified duration - Usage rk,duration (Use this at the end of the animation)

sp       - Allows the execution of setupParts (Useful when animating) - Usage sp,t or f

rmf      - Allows the execution of muzzle flash debuging - Usage rmf,t or f

rl       - Removes the last keyframe

pp       - Prints current part transform

start    - Starts the current animation

stop     - Stops the current animation

del      - Deletes the current animation

p        - Returns to the previuos keyframe transforms

n        - Goes to the next keyframe transform

showAnim - Converts the raw animation instructions to usable animation instructions


### Also you can move the gun parts and hands:

Left Arrow: moves the current part transform to -x

Right Arrow: moves the current part transform to +x

Up Arrow: moves the current part transform to +y

Down Arrow: moves the current part transform to -y

N key: moves the current part transform on +z

M key: moves the current part transform on -z

C key: changes the current mode to rotation and vice versa


### To animate the aim proccess
1.- You need to write debugAim(MatrixStack, float);

2.- Then you need to modify aim transform


### Want to reposition a gun part?
1.- You need to add doAbleToRepos(GunModelPart) on setupParts;

2.- Comment both parts setPartDisplayTransform and doAbleToRepos

