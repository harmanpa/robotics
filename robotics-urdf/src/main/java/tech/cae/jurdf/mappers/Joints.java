package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Joint;

import java.util.ArrayList;
import java.util.List;

public class Joints {
    public static List<tech.cae.robotics.urdf.Joint> convertMechanismJointsToUrdfJoints(List<Joint> mechanismJoints) {
        List<tech.cae.robotics.urdf.Joint> robotJoints = new ArrayList<>();
        for (Joint mechanismJoint : mechanismJoints) {
            robotJoints.add(convertJoint(mechanismJoint));
        }
        return robotJoints;
    }

    public static tech.cae.robotics.urdf.Joint convertJoint(Joint mechanismJoint) {
        tech.cae.robotics.urdf.Joint robotJoint = new tech.cae.robotics.urdf.Joint();
        robotJoint.setName(mechanismJoint.getName());
        robotJoint.setType(mechanismJoint.getType());

        return robotJoint;
    }
}
