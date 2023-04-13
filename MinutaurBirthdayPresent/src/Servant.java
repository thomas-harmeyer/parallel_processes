package src;

public class Servant implements Runnable {
  private PresentLinkedList presentList;
  private int[] presentsToAdd;
  private int[] presentsToRemove;

  public Servant(PresentLinkedList presentList, int[] presentsToAdd, int[] presentsToRemove) {
    this.presentList = presentList;
    this.presentsToAdd = presentsToAdd;
    this.presentsToRemove = presentsToRemove;
  }

  @Override
  public void run() {
    for (int i = 0; i < presentsToAdd.length; i++) {
      presentList.insert(new Present(presentsToAdd[i]));
      System.out.println("Servant " + Thread.currentThread().getId() + " added present " + presentsToAdd[i]);
      System.out.println("Current linked list state: " + presentList.toString());

      presentList.remove(presentsToRemove[i]);
      System.out.println("Servant " + Thread.currentThread().getId() + " removed present " + presentsToRemove[i]);
      System.out.println("Current linked list state: " + presentList.toString());
    }
  }

}