package cn.jimmiez.sample.demo;

import cn.jimmiez.pcu.common.graphics.HoppeEstimator;
import cn.jimmiez.pcu.io.ply.PlyReader;
import cn.jimmiez.pcu.model.PcuPointCloud3f;
import cn.jimmiez.sample.model.PointCloud;
import cn.jimmiez.sample.ui.PointCloudFrame;

import java.io.File;
import java.util.List;

public class ShowPointCloud {
    public static void main(String[] args) {
        File plyFile = new File(ShowPointCloud.class.getClassLoader().getResource("Dinosaur.ply").getFile());
        List<float[]> points = new PlyReader().readPointCloud(plyFile, PcuPointCloud3f.class).getPoint3ds();
        PointCloud pointCloud = new PointCloud(points);
        pointCloud.estimateNormal(new HoppeEstimator());

        PointCloudFrame frame = new PointCloudFrame(pointCloud);
    }
}
