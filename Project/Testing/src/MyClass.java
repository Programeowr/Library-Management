interface MyInterface {
    void imagine();
}

public class MyClass implements MyInterface, Drawable{

    @Override
    public void imagine() {
        System.out.println("Imagine");
    }

    @Override
    public void Draw() {
        System.out.println("Drawing a shape");
    }
}
