package cn.jimmiez.sample.demo;

import cn.jimmiez.pcu.alg.projector.WeightedLocallyOptimalProjector;
import cn.jimmiez.pcu.io.off.OffReader;
import cn.jimmiez.pcu.io.ply.PlyReader;
import cn.jimmiez.pcu.model.PointCloud3f;
import cn.jimmiez.sample.model.PointCloud;
import cn.jimmiez.sample.ui.PointCloudFrame;

import javax.vecmath.Point3d;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ShowReSample {

    public static void main(String[] args) throws FileNotFoundException {
        File plyFile = new File(ShowPointCloud.class.getClassLoader().getResource("NoisyBox.ply").getFile());
        List<float[]> points = new PlyReader().read(plyFile, PointCloud3f.class).getPoints();
        PointCloud pointCloud = new PointCloud(points);
        WeightedLocallyOptimalProjector wlop = new WeightedLocallyOptimalProjector(pointCloud.getPoints());
        List<Point3d> samples = new ArrayList<>();
        for (Point3d p : pointCloud.getPoints()) samples.add(new Point3d(p));
        System.out.println("Re-projecting sample points ... Please wait ...");
        wlop.project(samples, 30);

        PointCloud pointCloud2 = new PointCloud(samples, 0);

        PointCloudFrame frame = new PointCloudFrame(pointCloud);
        PointCloudFrame frame2 = new PointCloudFrame(pointCloud2);
    }

}
