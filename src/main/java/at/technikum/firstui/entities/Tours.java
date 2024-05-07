package at.technikum.firstui.entities;

public class Tours {

        private String name;
        private String description;
        private String from;
        private String to;
        private String transportType;
        private String distance;
        private String estimatedTime;

        public Tours(String name, String description, String from, String to, String transportType, String distance, String estimatedTime) {
            this.name = name;
            this.description = description;
            this.from = from;
            this.to = to;
            this.transportType = transportType;
            this.distance = distance;
            this.estimatedTime = estimatedTime;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }


    }

