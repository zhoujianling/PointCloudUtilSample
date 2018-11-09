package cn.jimmiez.sample;

import cn.jimmiez.sample.demo.ShowPointCloud;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        if (args[0].equals("ShowPointCloud")) {
            ShowPointCloud.main(args);
        }
    }
}
