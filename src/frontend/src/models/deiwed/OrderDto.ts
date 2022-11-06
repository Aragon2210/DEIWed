import DishDto from './DishDto';

export default class OrderDto {
    normalOption: DishDto = new DishDto();
    vegetarianOption: DishDto = new DishDto();
}