package gui.Shop;

import classes.domains.Item;
import classes.domains.User;
import classes.repositories.IShopRepository;
import classes.repositories.ShopRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

public class ShopController {
    private User user;
    private Item  item;
    @FXML
    private Label lbl_username;

    @FXML
    private Label lbl_credits;

    @FXML
    private TableView<Item> listItems;
    @FXML
    private ImageView imageItem;
    @FXML
    private TableColumn ID;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn type;
    public void setUser(User user) {
        this.user = user;
        try {
            updateUserInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateUserInfo() throws SQLException, IOException, ClassNotFoundException {
        lbl_username.setText(user.getUsername());
        lbl_credits.setText("★ " + user.getCredits().toString());
        getItems();
    }
    @FXML
    private void getItems() throws SQLException, IOException, ClassNotFoundException {
        listItems.getItems().clear();
        ID.setCellValueFactory(new PropertyValueFactory<Item,String>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Item,String>("name"));
        type.setCellValueFactory(new PropertyValueFactory<Item,String>("type"));
        IShopRepository shopRepository = new ShopRepository();
        if (!shopRepository.getItems(33).isEmpty())
        {
            for (Item i:shopRepository.getItems(33)) {
                listItems.getItems().add(i);
            }
        }

    }
    @FXML
    private void selectedItem()
    {
        if (listItems.getSelectionModel().getSelectedItem() != null)
        {
            item = listItems.getSelectionModel().getSelectedItem();
            imageItem.setImage(item.getImage());
        }
    }

    @FXML
    private void buySelectedItem() throws SQLException, IOException, ClassNotFoundException {
        if (item != null)
        {
            IShopRepository shopRepository = new ShopRepository();
            shopRepository.buyItem(item.getID(),33);
            getItems();
        }
    }





}