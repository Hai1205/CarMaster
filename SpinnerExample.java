import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpinnerExample {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("JSpinner Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // Tạo một JSpinner
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1); // giá trị bắt đầu, giá trị tối thiểu, tối đa, bước
        JSpinner spinner = new JSpinner(model);

        // Thêm JSpinner vào JFrame
        frame.add(spinner);

        // Tạo nút để lấy giá trị từ spinner
        JButton button = new JButton("Get Value");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (Integer) spinner.getValue(); // Lấy giá trị từ spinner
                JOptionPane.showMessageDialog(frame, "Giá trị: " + value);
            }
        });

        // Thêm nút vào JFrame
        frame.add(button);

        // Hiển thị JFrame
        frame.setVisible(true);
    }
}
