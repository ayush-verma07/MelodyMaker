import java.util.*;

public class Melody {
    
    private Queue<Note> notes;

    public Melody(Queue<Note> song) {
        notes = song;
    }

     public double getTotalDuration() {
        double totalDuration = 0.0;
        Queue<Note> tempQueue = new LinkedList<>(notes);
        boolean inRepeatSection = false;
        double repeatSectionDuration = 0.0;
    
        while (!tempQueue.isEmpty()) {
            Note note = tempQueue.poll();

            if(note.isRepeat() || inRepeatSection) {
                repeatSectionDuration += note.getDuration(); // Add the end of the repeated section
                if(inRepeatSection && note.isRepeat())
                {
                    inRepeatSection = false;
                    System.out.println(totalDuration + "-" + repeatSectionDuration);
                    totalDuration += (repeatSectionDuration * 2);
                    repeatSectionDuration = 0.0; // Reset the repeated section duration
                } else if (note.isRepeat() && !inRepeatSection) {
                    inRepeatSection = true;
                }

            }
            else {
                totalDuration += note.getDuration();
            }
        }
    
        return totalDuration;
    }
    

    public String toString() {

        String result = "";
        Queue<Note> tempQueue = new LinkedList<>(notes);

        while(!tempQueue.isEmpty()) {
            result += tempQueue.poll().toString();
            result += "\n";
        }
        return result;
    }

    public void changeTempo(double tempo) {

        Queue<Note> tempQueue = new LinkedList<>(notes);

        while (!tempQueue.isEmpty()) {
            tempQueue.peek().setDuration(tempQueue.peek().getDuration() * tempo);
            tempQueue.poll();
        }
    }

    public void reverse() {
        Queue<Note> reverse = new LinkedList<>();
        Queue<Note> tempQueue = new LinkedList<>(notes);
        Stack<Note> stack = new Stack<>();

        while (!tempQueue.isEmpty()) {
            stack.add(tempQueue.peek());
            tempQueue.remove();
        }
        while (!stack.isEmpty()) {
            tempQueue.add(stack.peek());
            stack.pop();
        }

        notes = tempQueue;

    }

    public void append(Melody other) {
        Queue<Note> otherNotes = new LinkedList<>(other.getNotes());

        while (!otherNotes.isEmpty()) {
            notes.add(otherNotes.poll());
        }
    }

public void play() {
    Queue<Note> mainQueue = new LinkedList<>(notes);
    Queue<Note> secondaryQueue = new LinkedList<>();
    boolean inRepeatSection = false;

    while (!mainQueue.isEmpty()) {
        Note note = mainQueue.poll();

        if (note.isRepeat()) {
            if (inRepeatSection) {
                // Encountered the end of the repeated section
                inRepeatSection = false;
                 // Add the note at the end of the repeated section to the secondary queue
                 secondaryQueue.add(note);
                // Play all the notes in the secondary queue twice
                for (int i = 0; i < 2; i++) {
                    Queue<Note> tempQueue = new LinkedList<>(secondaryQueue);
                    while (!tempQueue.isEmpty()) {
                        System.out.println("Played" + tempQueue.peek().toString());
                        tempQueue.poll().play();
                    }
                }
                secondaryQueue.clear();
            } else {
                // Encountered the start of a repeated section
                inRepeatSection = true;                
            }
        }

        if (inRepeatSection) {
            // Add the note to the secondary queue
            secondaryQueue.add(note);
        } else {
            // Play the note immediately
            System.out.println("Played" + note.toString());
            note.play();
        }
    }
}


    public Queue<Note> getNotes() {
        return notes;
    }

}
