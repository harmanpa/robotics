package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Body;

public class Inertial {
    public static tech.cae.robotics.urdf.Inertial convertInertial(Body body) {
        tech.cae.robotics.urdf.Inertial inertial = new tech.cae.robotics.urdf.Inertial();
        inertial.setInertia(Inertia.convertInertia(body));
        inertial.setMass(Mass.convertMass(body));
        inertial.setOrigin(Pose.convertPose(body));
        return inertial;
    }
}
