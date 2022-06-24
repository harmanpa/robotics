package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Body;

import java.util.Arrays;

public class Pose {
    static public tech.cae.robotics.urdf.Pose convertPose(Body body) {
        tech.cae.robotics.urdf.Pose pose = new tech.cae.robotics.urdf.Pose();
        //TODO: Fix it
        String xyz = Arrays.toString(body.getCs().getOrigin()); //check if that toString is OK
        String rpy = " 0 0 0";
        pose.setXyz(xyz);
        pose.setRpy(rpy);
        return pose;
    }
}
