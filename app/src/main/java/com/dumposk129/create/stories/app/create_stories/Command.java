package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129 on 6/24/2015.
 */
public class Command {
    public static String genFrame(String path_pic, String path_audio, String path_output) {
        return "ffmpeg -loop 1 -i "+path_pic + " -i " + path_audio + " -c:v libx264 -tune stillimage -c:a aac -strict experimental -b:a 192k -pix_fmt yuv420p -shortest " +path_output;
    }
}
