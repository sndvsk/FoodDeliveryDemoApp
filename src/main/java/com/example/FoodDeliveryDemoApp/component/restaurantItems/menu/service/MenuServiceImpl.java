package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public MenuServiceImpl(MenuRepository menuRepository,
                           ItemRepository itemRepository,
                           RestaurantRepository restaurantRepository,
                           OwnerRepository ownerRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    @Transactional
    public List<MenuDTO> getAllMenus() {
        List<Menu> listOfMenus = menuRepository.findAll();
        if (listOfMenus.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return MenuDTOMapper.toDtoList(listOfMenus);
    }

    @Transactional
    public List<MenuDTO> getMenusByOwnerId(Long ownerId) {
        List<Menu> menus = ownerRepository.findById(ownerId)
                .map(Owner::getMenus)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
        return MenuDTOMapper.toDtoList(menus);
    }

    @Transactional
    public MenuDTO getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
        return MenuDTOMapper.toDto(menu);
    }

    @Transactional
    public List<MenuDTO> getMenusOfRestaurant(Long restaurantId) {
        List<Menu> menus = restaurantRepository.findById(restaurantId)
                .map(Restaurant::getMenus)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        return MenuDTOMapper.toDtoList(menus);
    }

    @Transactional
    public MenuDTO addMenu(Long ownerId, String menuName) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setVisibility(false);
        menu.setOwner(owner);
        menuRepository.save(menu);
        return MenuDTOMapper.toDto(menu);
    }

    @Transactional
    public ItemDTO addItemToMenu(Long itemId, Long menuId, Long restaurantId, Long ownerId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());
        OwnershipHelper.validateRestaurant(restaurantId, menu.getRestaurant().getId());

        return itemRepository.findById(itemId).map(item -> {
            item.setMenu(menu);
            itemRepository.save(item);
            return ItemDTOMapper.toDto(item);
        }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    @Transactional
    public MenuDTO addMenuToRestaurant(Long menuId, Long restaurantId, Long ownerId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant = optionalRestaurant
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return MenuDTOMapper.toDto(menu);
    }

    @Transactional
    public MenuDTO toggleMenuVisibility(Long menuId, Long ownerId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());
                    menu.setVisibility(!menu.isVisibility());
                    menuRepository.save(menu);
                    return MenuDTOMapper.toDto(menu);
                })
                .orElseThrow(() -> new CustomBadRequestException("Unexpected error. Menu was not updated."));
    }

    @Transactional
    public MenuDTO patchMenu(Long menuId, String menuName, Long ownerId) {
        return menuRepository.findById(menuId).map(menu -> {
            /*Long expectedRestaurantId = menu.getRestaurant().getId();
            if (!expectedRestaurantId.equals(restaurantId)) {
                throw new CustomBadRequestException(
                        String.format("Id of restaurant is wrong. Expected: %s, provided: %s",
                                expectedRestaurantId, restaurantId));
            }*/

            OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());
            //OwnershipHelper.validateRestaurant(restaurantId, menu.getRestaurant().getId());

            Optional.ofNullable(menuName).ifPresent(menu::setName);

            menuRepository.save(menu);
            return MenuDTOMapper.toDto(menu);
        }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

    @Transactional
    public String deleteMenuFromRestaurant(Long menuId, Long restaurantId, Long ownerId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());
                    OwnershipHelper.validateRestaurant(restaurantId, menu.getRestaurant().getId());

                    menu.setRestaurant(null);
                    menuRepository.save(menu);
                    return "Deleted menu from restaurant with id " + menuId;
                }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

    @Transactional
    public String deleteMenu(Long menuId, Long ownerId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());

                    menuRepository.delete(menu);
                    return String.format("Menu with id: %s was deleted", menuId);
                })
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

}
