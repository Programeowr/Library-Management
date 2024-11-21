interface Drawable {
    void Draw();
}

public class Shape implements Drawable{
    public String color;
    public boolean filled;

    Shape(){
        this.color = "red";
        this.filled = true;
    }

    Shape(String col, boolean fill) {
        this.color = col;
        this.filled = fill;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String col){
        this.color = col;
    }

    public boolean isFilled(){
        return this.filled;
    }

    public void setFilled(boolean fill){
        this.filled = fill;
    }

    String tooString(){
        return "Shape";
    }

    @Override
    public void Draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    double radius;

    Circle() {
        this.color = "red";
        this.filled = true;
        this.radius = 1;
    }

    Circle(double r) {
        this.radius = r;
    }

    Circle(int r) {
        this.radius = r;
    }

    double getRadius() {
        return this.radius;
    }

    void setRadius(double r) {
        this.radius = r;
    }

    double getArea() {
        return (3.14) * (this.radius) * (this.radius);
    }

    double getPerimeter() {
        return 2 * (3.14) * (this.radius);
    }

    String tooString(){
        return "Circle";
    }

}

class Rectangle extends Shape{
    double width;
    double length;

    Rectangle(){
        this.color = "red";
        this.filled = true;
        this.width = 1;
        this.length = 2;
    }

    Rectangle(double w, double l){
        this.width = w;
        this.length = l;
    }

    double getWidth(){
        return this.width;
    }

    void setWidth(double w){
        this.width = w;
    }

    double getLength(){
        return this.length;
    }

    void setLength(double l){
        this.length = l;
    }

    double getArea(){
        return (this.length)*(this.width);
    }

    double getPerimeter(){
        return 2*((this.length) + (this.width));
    }

    String tooString(){
        return "Rectangle";
    }

}

class Square extends Rectangle{
    double side;

    Square(){
        this.color = "red";
        this.filled = true;
        this.side = 1;
    }

    Square(double s){
        this.side = s;
    }

    double getSide(){
        return this.side;
    }

    void setSide(double s){
        this.side = s;
    }

    double getArea(){
        return (this.side)*(this.side);
    }

    double getPerimeter(){
        return 4*(this.side);
    }

    String tooString(){
        return "Square";
    }

}
