package hw08.serialization.visitor;

public interface Visitor {
    void visit(Integer value, String name);

    void visit(Double value, String name);

    void visit(Float value, String name);

    void visit(Long value, String name);

    void visit(Short value, String name);

    void visit(Byte value, String name);

    void visit(Character value, String name);

    void visit(String value, String name);

    void visit(Boolean value, String name);

    void visitNull(String name);

    void visitCollectionEnter(String name);

    void visitCollectionExit();

    void visitObjectEnter(String name);

    void visitObjectExit();
}
