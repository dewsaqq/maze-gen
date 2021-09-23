package graph;

public class Vertex {
    private final Type vertexType;

    public Vertex(Type vertexType) {
        this.vertexType = vertexType;
    }

    public Vertex() {
        this(Type.PLAIN);
    }

    public Type getVertexType() {
        return vertexType;
    }

    @Override
    public String toString() {
        switch (vertexType) {
            case END: return "E";
            case PLAIN: return "P";
            case START: return "S";
            case DEAD_END: return "D";
            case FOUR_WAY_INTERSECTION: return "4";
            case THREE_WAY_INTERSECTION: return "3";
        }

        return null;
    }

    enum Type {
        PLAIN,
        START,
        END,
        DEAD_END,
        THREE_WAY_INTERSECTION,
        FOUR_WAY_INTERSECTION
    }
}


