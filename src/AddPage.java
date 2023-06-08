import javax.swing.*;

public class AddPage {
    private final JFrame addFrame;
    private final JPanel contentPanel;

    public AddPage() {
        addFrame = new JFrame("Add New Friend");
        contentPanel = new JPanel();
        addFrame.setSize(500,300);
        addFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
