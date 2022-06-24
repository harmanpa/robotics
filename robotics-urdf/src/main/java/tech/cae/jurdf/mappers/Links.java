package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Body;
import tech.cae.robotics.urdf.Link;

import java.util.ArrayList;
import java.util.List;

public class Links {
    public static List<Link> convertMechanismBodiesToUrdfLinks(List<Body> bodies) {
        List<Link> links = new ArrayList<>();
        for (Body body : bodies) {
            links.add(convertLink(body));
        }
        return links;
    }

    private static Link convertLink(Body body) {
        Link link = new Link();
        // TODO: Hardcoded name
        link.setName("Link Name");
        link.setInertial(Inertial.convertInertial(body));
        return link;
    }
}
