package src;
public class PresentNode {
  private Present present;
  private PresentNode next;

  public PresentNode(Present present) {
    this.present = present;
  }

  public Present getPresent() {
    return present;
  }

  public PresentNode getNext() {
    return next;
  }

  public void setNext(PresentNode next) {
    this.next = next;
  }
}
