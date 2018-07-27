package sample;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class NodeEventHandler implements EventHandler<MouseEvent> {

    private final EventHandler<MouseEvent> onDraggedEventHandler;

    private final EventHandler<MouseEvent> onClickedEventHandler;

    private boolean dragging = false;

    public NodeEventHandler(EventHandler<MouseEvent> onDraggedEventHandler, EventHandler<MouseEvent> onClickedEventHandler) {
        this.onDraggedEventHandler = onDraggedEventHandler;
        this.onClickedEventHandler = onClickedEventHandler;
    }


    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            dragging = false;
            //System.out.println("pressed");
            NodeMover.getInstance().SwipeClick(event);

        } else if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            dragging = true;
            //System.out.println("drag  detect");

        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED){
            NodeMover.getInstance().SwipeRelease(event);
            //System.out.println("released");

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            //maybe filter on dragging (== true)
            onDraggedEventHandler.handle(event);
            NodeMover.getInstance().SwipeDrag(event);
            //System.out.println("dragging");

        } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            //System.out.println("clicked");

            if (!dragging) {
                onClickedEventHandler.handle(event);
            }
        }
    }
}
