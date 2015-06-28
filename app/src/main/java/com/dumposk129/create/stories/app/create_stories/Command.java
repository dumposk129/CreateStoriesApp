package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129 on 6/24/2015.
 */
public class Command {
    public static String genFrame(String path_pic, String path_audio, String path_output) {
       /* return "ffmpeg -loop 1 -i " + path_pic + " -i " + path_audio +
                " -c:v libx264 -tune stillimage -c:a aac -strict experimental -b:a 192k -pix_fmt yuv420p -shortest " + path_output;*/
        /*return "ffmpeg -y -i " + path_audio + " -i " + path_pic +
                " -strict experimental -vf transpose=1 -s 160*120 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k /StoryApp/StoryName/Video/f1.mp4" ;*/
        return "ffmpeg -loop 1 -i "+path_pic + " -i " + path_audio + " -y -r 1 -b:v 2500k -acodec copy -ab 384k -vcodec mpeg4 "+ path_output+".mp4";
    }
}