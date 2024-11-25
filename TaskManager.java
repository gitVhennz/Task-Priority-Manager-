import java.util.Scanner;

class Task {
    int priority;
    String description;

    public Task(int priority, String description) {
        this.priority = priority;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Priority: " + priority + " - " + description;
    }
}

class MaxHeap {
    private Task[] heap;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public MaxHeap() {
        heap = new Task[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(Task task) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = task;
        size++;
        heapifyUp();
    }

    public Task remove() {
        if (size == 0) return null;
        Task root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return root;
    }

    public Task peek() {
        return size > 0 ? heap[0] : null;
    }

    public void displayTasks() {
        for (int i = 0; i < size; i++) {
            System.out.println(heap[i]);
        }
    }

    private void resize() {
        Task[] newHeap = new Task[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    private void heapifyUp() {
        int index = size - 1;
        while (index > 0 && heap[index].priority > heap[(index - 1) / 2].priority) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (2 * index + 1 < size) {
            int largerChildIndex = 2 * index + 1;
            if (2 * index + 2 < size && heap[2 * index + 2].priority > heap[largerChildIndex].priority) {
                largerChildIndex = 2 * index + 2;
            }
            if (heap[index].priority >= heap[largerChildIndex].priority) {
                break;
            }
            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private void swap(int i, int j) {
        Task temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

public class TaskManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaxHeap heap = new MaxHeap();

        while (true) {
            System.out.println("Task Manager Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Remove Task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task priority (1 - high, 10 - low): ");
                    int priority = scanner.nextInt();
                    heap.add(new Task(priority, description));
                    break;
                case 2:
                    System.out.println("Tasks in priority order:");
                    heap.displayTasks();
                    break;
                case 3:
                    Task removedTask = heap.remove();
                    if (removedTask != null) {
                        System.out.println("Removed task: " + removedTask);
                    } else {
                        System.out.println("No tasks to remove.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting Task Manager...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}