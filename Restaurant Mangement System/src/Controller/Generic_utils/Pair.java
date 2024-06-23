package Controller.Generic_utils;

public class Pair <T>{
    private T first,second;
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() {
        return first; }
    public T getSecond() {
        return second; }
    public void setFirst(T newValue) {
        first = newValue; }
    public void setSecond(T newValue) {
        second = newValue;
    }

}
