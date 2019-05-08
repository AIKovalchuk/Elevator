package Algoritms;

public class Order {
    private int floor;
    private int next;
    private int weight;
    private Direction direction;

    public Order(){
        this.floor = (int)(Math.random() * 20) + 1;
        this.direction = Math.random() * 2 == 0 ? Direction.UP : Direction.DOWN;
        {
            if (floor == 20) {
                this.direction = Direction.DOWN;
            }
            if (floor == 1) {
                this.direction = Direction.UP;
            }
        }
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getNext() {
        while (next == 0 || next == floor) {
            if (direction.equals(Direction.UP)){
                next = (int)(Math.random() * 20) + floor;
            }
            else {
                next = (int)(Math.random() * floor) + 1;
            }
        }
        return next;
    }


    public int getWeight() {
        if(weight==0) {
            weight = (int) (Math.random() * 110) + 30;
        }
        return weight;
    }
}
