package fr.istic.sit.codisgroupea.model.message;

public class LocationMessage {

        private Location location;

        private double altitude;

        public LocationMessage(double lat, double lng, double alt){
            this.altitude = alt;
            this.location = new Location(lat, lng);
        }

	    public static class Location{

            private double lat;

            private double lng;

            public double getLat () {
                return lat;
            }

            public void setLat ( double lat){
                this.lat = lat;
            }

            public double getLng () {
                return lng;
            }

            public void setLng ( double lng){
                this.lng = lng;
            }

            public Location( double lat, double lng){
                this.lat = lat;
                this.lng = lng;
            }
        }
}
