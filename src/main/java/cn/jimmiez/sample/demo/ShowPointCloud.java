package cn.jimmiez.sample.demo;

import cn.jimmiez.pcu.alg.normal.HoppeEstimator;
import cn.jimmiez.pcu.alg.projector.OctreeVoxelizer;
import cn.jimmiez.pcu.alg.skeleton.LevelSetSkeleton;
import cn.jimmiez.pcu.io.ply.PlyReader;
import cn.jimmiez.pcu.model.PcuPointCloud3f;
import cn.jimmiez.sample.model.PointCloud;
import cn.jimmiez.sample.ui.PointCloudFrame;

import java.io.File;
import java.util.List;

public class ShowPointCloud {
    public static void main(String[] args) {
        File plyFile = new File(ShowPointCloud.class.getClassLoader().getResource("Y.ply").getFile());
        List<float[]> points = new PlyReader().readPointCloud(plyFile, PcuPointCloud3f.class).getPoint3ds();
        PointCloud pointCloud = new PointCloud(points);
        pointCloud.estimateNormal(new HoppeEstimator());
        LevelSetSkeleton skel = new LevelSetSkeleton();
        skel.setN(15);
        skel.setK(20);
        pointCloud.skeletonize(skel);
//        pointCloud.voxelize(new OctreeVoxelizer());

        PointCloudFrame frame = new PointCloudFrame(pointCloud);
    }
}
