package backend.team3.pro.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "vaatteet")
public class Vaatte {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;



        private String name;
        private String size;
        private double price;
        private String color;
        private String Type;
        private String manufacturer;
    
        public Vaatte(String name, String size, double price) {
            this.name = name;
            this.size = size;
            this.price = price;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getSize() {
            return size;
        }
    
        public void setSize(String size) {
            this.size = size;
        }
    
        public double getPrice() {
            return price;
        }
    
        public void setPrice(double price) {
            this.price = price;
        }
        
        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
}
