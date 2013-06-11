#compile using http://www.swftools.org/

.flash filename="text.swf"
.font times "Times.swf"
.text richfaces text="RichFaces" font=times size=200% color=blue
.text mediaoutput text="mediaOutput" font=times size=100% color=red

.frame 1
        .put richfaces pin=center x=50 y=50 
        .put mediaoutput pin=center x=50 y=50 alpha=25%
.frame 200
     .change richfaces rotate+=360 pin=center alpha=25% 
     .change mediaoutput rotate-=360 pin=center alpha=100% 
.end