//@@author amad-person
package seedu.address.testutil;

import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setOrderInformation(order.getOrderInformation());
        descriptor.setPrice(order.getPrice());
        descriptor.setQuantity(order.getQuantity());
        descriptor.setDeliveryDate(order.getDeliveryDate());
    }

    /**
     * Sets the {@code OrderInformation} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withOrderInformation(String orderInformation) {
        descriptor.setOrderInformation(new OrderInformation(orderInformation));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDeliveryDate(String deliveryDate) {
        descriptor.setDeliveryDate(new DeliveryDate(deliveryDate));
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
