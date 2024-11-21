public class Main{
    public static void main(String[] args){
        Circle C1 = new Circle();
        Circle C2 = new Circle(2.5);
        Circle C3 = new Circle(3);


        Rectangle R1 = new Rectangle();
        Rectangle R2 = new Rectangle(2, 3);

        Square S1 = new Square();
        Square S2 = new Square(2);

        System.out.println(C1.getColor());
        C1.setColor("blue");
        System.out.println(C1.getColor());

        System.out.println(C2.getArea());
        C2.setRadius(2.5);
        System.out.println(C2.getArea());

        System.out.println(C3.getArea());

        System.out.println(R1.getPerimeter());
        R1.setLength(4);
        System.out.println(R1.getPerimeter());

        System.out.println(R2.getArea());
        R2.setWidth(1);
        System.out.println(R2.getArea());

        System.out.println(R1.tooString());
        System.out.println(S1.tooString());

        MyClass cl = new MyClass();
        cl.imagine();
        cl.Draw();

    }
}