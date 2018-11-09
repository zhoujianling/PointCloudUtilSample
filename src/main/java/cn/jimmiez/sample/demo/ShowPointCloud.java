package cn.jimmiez.sample.demo;

import cn.jimmiez.sample.ui.PointCloudFrame;

import javax.media.j3d.BranchGroup;
import java.io.File;

public class ShowPointCloud {
    public static void main(String[] args) {
        File f = new File(ShowPointCloud.class.getClassLoader().getResource("Dinosaur.ply").getFile());
        PointCloudFrame frame = new PointCloudFrame(f);
    }
}
