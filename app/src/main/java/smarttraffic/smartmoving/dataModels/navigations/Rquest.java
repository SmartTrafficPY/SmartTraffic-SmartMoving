package smarttraffic.smartmoving.dataModels.navigations;

import smarttraffic.smartmoving.dataModels.Point;

public class Rquest {

    private String type = "Feature";
    private NavigationRequestProperties properties;
    private Point geometry = null;

        @Override
        public String toString() {
            return "NavigationRequests {" +
                    "type='" + type + '\'' +
                    ", properties=" + properties + '\'' +
                    ", geometry=" + geometry + '\'' +
                    '}';
        }



}
