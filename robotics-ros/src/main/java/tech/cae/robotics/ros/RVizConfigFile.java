/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import tech.cae.robotics.urdf.Robot;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tech.cae.robotics.urdf.Link;

/**
 *
 * @author peter
 */
public class RVizConfigFile {

    private final Map<String, Object> data;

    public RVizConfigFile() {
        this.data = new HashMap<>();
    }
    
    public static RVizConfigFile getDefault() throws IOException {
        RVizConfigFile config = new RVizConfigFile();
        config.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("tech/cae/robotics/ros/default.rviz"));
        return config;
    }
    
    void merge(Map<String, Object> target, Map<String, Object> src) {
        for(Map.Entry<String, Object> entry : src.entrySet()) {
            if(target.containsKey(entry.getKey()) 
                    && target.get(entry.getKey()) instanceof Map
                    && entry.getValue() instanceof Map) {
                merge((Map<String, Object>)target.get(entry.getKey()), (Map<String, Object>)entry.getValue());
            } else {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    Map<String, Object> getOrCreateMap(String[] path) {
        Map<String, Object> parent;
        switch(path.length) {
            case 0:
                return data;
            case 1:
                parent = data;
                break;
            default:
                parent = getOrCreateMap(Arrays.copyOfRange(path, 0, path.length-1));
        }
        if(!parent.containsKey(path[path.length-1])) {
            parent.put(path[path.length-1], new HashMap<String, Object>());
        }
        return (Map<String, Object>)parent.get(path[path.length-1]);
    }
    
    List<Object> getOrCreateList(String[] path) {
        Map<String, Object> parent = getOrCreateMap(Arrays.copyOfRange(path, 0, path.length-1));
        if(!parent.containsKey(path[path.length-1])) {
            parent.put(path[path.length-1], new ArrayList<Object>());
        }
        return (List<Object>)parent.get(path[path.length-1]);
    }
    
    public void set(String[] path, Object value) {
        if(value instanceof RVizConfigFile) {
            set(path, ((RVizConfigFile)value).data);
            return;
        }
        getOrCreateMap(Arrays.copyOfRange(path, 0, path.length-1))
                .put(path[path.length-1], value);
    }
    
    public void add(String[] path, Object value) {
        if(value instanceof RVizConfigFile) {
            add(path, ((RVizConfigFile)value).data);
            return;
        }
        getOrCreateList(path).add(value);
    }
    
    public Object get(String[] path) {
        return getOrCreateMap(Arrays.copyOfRange(path, 0, path.length-1))
                .get(path[path.length-1]);        
    }
    
    public void forRobot(Robot robot, String baseLink) {        
        String robotName = robot.getName();
        if(baseLink != null) {
            set(new String[]{"Visualization Manager", "Global Options", "Fixed Frame"}, baseLink);
        }
        // We make an instance of RVizConfigFile to represent the display object, we'll then add it as a subnode
        RVizConfigFile display = new RVizConfigFile();
        display.set(new String[]{"Alpha"}, 1);
        display.set(new String[]{"Class"}, "rviz/RobotModel");
        display.set(new String[]{"Collision Enabled"}, false);
        display.set(new String[]{"Enabled"}, true);
        display.set(new String[]{"Links", "All Links Enabled"}, true);
        display.set(new String[]{"Links", "Expand Joint Details"}, false);
        display.set(new String[]{"Links", "Expand Link Details"}, false);
        display.set(new String[]{"Links", "Expand Tree"}, false);
        display.set(new String[]{"Links", "Link Tree Style"}, "Links in Alphabetic Order");
        display.set(new String[]{"Name"}, robotName);
        display.set(new String[]{"Robot Description"}, "robot_description");
        display.set(new String[]{"TF Prefix"}, "");
        display.set(new String[]{"Update Interval"}, 0);
        display.set(new String[]{"Value"}, true);
        display.set(new String[]{"Visual Enabled"}, true);
        robot.getJointsAndLinksAndMaterials().stream()
                .filter(jlm -> jlm instanceof Link)
                .map(jlm -> (Link)jlm)
                .forEach(link -> {
                    display.set(new String[]{"Links", "Link Tree Style", link.getName(), "Alpha"}, 1);
                    display.set(new String[]{"Links", "Link Tree Style", link.getName(), "Show Axes"}, false);
                    display.set(new String[]{"Links", "Link Tree Style", link.getName(), "Show Trail"}, false);
                    display.set(new String[]{"Links", "Link Tree Style", link.getName(), "Value"}, true);
                });
        add(new String[]{"Visualization Manager", "Displays"}, display);
    }

    public void read(InputStream is) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map map = mapper.readValue(is, Map.class);
        merge(data, map);
    }

    public void write(File target) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(target, data);
    }
}
