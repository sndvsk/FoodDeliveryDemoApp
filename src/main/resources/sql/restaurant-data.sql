-- restaurants.sql

INSERT INTO restaurants(name, description, theme, phone, image, owner_id)
VALUES ('Asian Delights', 'Specializes in various Asian dishes', 'ASIAN', '+123456789', null, 1),
       ('Mexican Fiesta', 'Authentic Mexican food', 'MEXICAN', '+234567891', null, 2),
       ('Bella Italia', 'Delicious Italian food', 'ITALIAN', '+345678912', null, 3),
       ('Parisian Bistro', 'Traditional French cuisine', 'FRENCH', '+456789123', null, 4),
       ('American Diner', 'Classic American dishes', 'AMERICAN', '+567891234', null, 5);

-- menus.sql

INSERT INTO menus(name, hidden, restaurant_id, owner_id)
VALUES ('Asian Menu', 'YES', 1, 1),
       ('Mexican Menu', 'YES', 2, 2),
       ('Italian Menu', 'YES', 3, 3),
       ('French Menu', 'YES', 4, 4),
       ('American Menu', 'YES', 5, 5);

-- items.sql

-- Continue items.sql for Asian Menu. 20 items

INSERT INTO items(name, description, price, image, ingredients, allergens, menu_id, restaurant_id, owner_id)
VALUES
('Pad Thai', 'Pad Thai is a Thai noodle stir fry with a sweet-savoury-sour sauce scattered with crushed peanuts.', 9.99, null, 'rice noodles,peanut or vegetable oil,medium shrimp,eggs,palm sugar,Thai fish sauce,tamarind puree,fresh lime juice,cayenne pepper,shallot,garlic,scallions,bean sprouts,chopped peanuts', 'peanuts', 1, 1, 1),
('Green Curry', 'Green Curry is a spicy Thai curry made with green curry paste, coconut milk, meat or vegetables, and herbs.', 11.99, null, 'green curry paste,coconut milk,meat or vegetables,Thai basil leaves,Thai eggplant,bell peppers,kaffir lime leaves', null, 1, 1, 1),
('Sushi Roll', 'Sushi Roll is a Japanese dish consisting of sushi rice and various ingredients such as raw or cooked seafood, vegetables, and nori (seaweed).', 13.99, null, 'sushi rice,nori,raw or cooked seafood,vegetables', 'fish', 1, 1, 1),
('Chicken Satay', 'Chicken Satay is a popular Southeast Asian dish made with marinated and grilled chicken skewers served with peanut sauce.', 8.99, null, 'chicken breast,lemongrass,garlic,soy sauce,fish sauce,turmeric powder,coconut milk,peanut butter,lime juice,brown sugar', 'peanuts,soybeans', 1, 1, 1),
('Beef Pho', 'Beef Pho is a traditional Vietnamese soup made with beef broth, rice noodles, thinly sliced beef, and aromatic herbs and spices.', 10.99, null, 'beef broth,rice noodles,thinly sliced beef (e.g., sirloin),onion,ginger,cinnamon star anise,cloves,fish sauce,lime wedges,bean sprouts,basil leaves,cilantro leaves', 'fish', 1, 1, 1),
('Chicken Teriyaki', 'Japanese inspired dish served with teriyaki sauce', 12.99, null, 'chicken,soy sauce,sugar,garlic,ginger,cornstarch', 'soybeans', 1, 1, 1),
('Fried Rice', 'Classic Asian dish with vegetables and proteins', 8.99, null, 'rice,vegetable oil,garlic,eggs,soy sauce,peas,carrots,green onions', 'soybeans', 1, 1, 1),
('Miso Soup', 'Traditional Japanese soup with tofu and seaweed', 4.99, null, 'water,dashi granules,miso paste,tofu,green onions,seaweed', 'soybeans,fish', 1, 1, 1),
('Kimchi', 'Korean traditional side dish made from fermented cabbage', 5.99, null, 'napa cabbage,radish,scallions,garlic,ginger,chili powder,fish sauce,rice flour,sugar,salt', 'fish', 1, 1, 1),
('Dumplings', 'Delicious dumplings filled with meat and vegetables', 8.99, null, 'flour,water,cabbage,pork,scallions,soy sauce,ginger,salt,pepper,vegetable oil', 'soybeans', 1, 1, 1),
('Ramen', 'Japanese noodle soup with meat and vegetables', 11.99, null, 'chicken broth,soy sauce,mirin,garlic,ginger,ramen noodles,pork belly,green onions,seaweed,boiled eggs', 'soybeans,wheat', 1, 1, 1),
('Spring Rolls', 'Fresh vegetables and meat wrapped in rice paper', 7.99, null, 'rice paper,lettuce,cilantro,mint,vermicelli,shrimp,pork', 'shellfish', 1, 1, 1),
('Kung Pao Chicken', 'Spicy, stir-fried Chinese dish with chicken, peanuts, and vegetables', 13.99, null, 'chicken,peanuts,vegetable oil,soy sauce,vinegar,sugar,garlic,ginger,dried chili peppers,green onions', 'peanuts,soybeans', 1, 1, 1),
('Chicken Tikka Masala', 'Creamy Indian dish with spiced chicken', 14.99, null, 'chicken,vegetable oil,yogurt,garlic,ginger,garam masala,cumin,paprika,cayenne pepper,onions,tomatoes,cream', 'milk', 1, 1, 1),
('Mango Lassi', 'Refreshing Indian mango and yogurt drink', 3.99, null, 'mango,yogurt,sugar,water', 'milk', 1, 1, 1),
('Bubble Tea', 'Sweet tea beverage with tapioca pearls', 4.99, null, 'tea,milk,sugar,tapioca pearls', 'milk', 1, 1, 1),
('Sake', 'Japanese rice wine', 6.99, null, 'rice,water,koji mold,yeast', null, 1, 1, 1),
('Soju', 'Korean distilled alcohol', 7.99, null, 'rice,wheat,yeast', 'wheat', 1, 1, 1),
('Tsingtao Beer', 'Chinese lager beer', 4.99, null, 'water,barley malt,rice,hops,yeast', 'wheat', 1, 1, 1),
('Matcha Green Tea', 'Powdered green tea', 2.99, null, 'matcha powder,water,sugar,milk', 'milk', 1, 1, 1);

-- Continue items.sql for Mexican Menu. 20 items

INSERT INTO items(name, description, price, image, ingredients, allergens, menu_id, restaurant_id, owner_id)
VALUES
('Tacos', 'Classic Mexican dish with various fillings', 7.99, null, 'tortillas,beef,onions,cilantro,avocado,lime', null, 2, 2, 2),
('Chiles Rellenos', 'Poblano peppers stuffed with cheese', 8.99, null, 'poblano peppers,queso fresco,flour,eggs,tomato sauce', 'milk', 2, 2, 2),
('Chicken Enchiladas', 'Tortillas filled with chicken and covered with chili sauce', 10.99, null, 'chicken,tortillas,enchilada sauce,cheddar cheese,monterey jack cheese', 'milk', 2, 2, 2),
('Ceviche', 'Fresh seafood marinated in citrus juices', 13.99, null, 'fish,shrimp,lime juice,lemon juice,orange juice,tomatoes,onions,cilantro,avocado', 'fish,shellfish', 2, 2, 2),
('Churros', 'Fried dough pastry with sugar', 4.99, null, 'water,butter,salt,sugar,flour,eggs,vegetable oil,cinnamon', 'milk,wheat', 2, 2, 2),
('Guacamole', 'Avocado based dip', 5.99, null, 'avocado,onion,garlic,cilantro,lime juice,tomato,jalapeno', null, 2, 2, 2),
('Nachos', 'Tortilla chips with cheese and toppings', 8.99, null, 'tortilla chips,cheddar cheese,jalapenos,tomatoes,onions,sour cream', 'milk', 2, 2, 2),
('Tamale', 'Corn dough filled with meats, cheese or fruits', 9.99, null, 'masa,vegetable shortening,chicken broth,fillings (chicken, cheese, or fruits)', 'milk', 2, 2, 2),
('Carnitas', 'Slow cooked pork dish', 12.99, null, 'pork shoulder,garlic,orange juice,lime juice,cumin,oregano,vegetable oil', null, 2, 2, 2),
('Flan', 'Caramel custard dessert', 5.99, null, 'sugar,eggs,condensed milk,evaporated milk,vanilla extract', 'milk,eggs', 2, 2, 2),
('Horchata', 'Refreshing rice drink', 3.99, null, 'rice,almonds,cinnamon,vanilla extract,sugar,milk', 'milk,nuts', 2, 2, 2),
('Margarita', 'Classic tequila cocktail', 7.99, null, 'tequila,lime juice,triple sec,ice,salt', null, 2, 2, 2),
('Mexican Beer', 'Refreshing lager beer', 4.99, null, 'water,barley malt,hops,yeast', 'wheat', 2, 2, 2),
('Tequila', 'Distilled spirit made from the blue agave plant', 6.99, null, 'blue agave', null, 2, 2, 2),
('Mezcal', 'Smoky distilled spirit made from the agave plant', 7.99, null, 'agave', null, 2, 2, 2),
('Sangria', 'Fruity wine punch', 6.99, null, 'red wine,brandy,orange juice,lemon juice,apple,orange,sugar,club soda', null, 2, 2, 2),
('Chiles Rellenos', 'Stuffed, battered, and fried chili peppers', 10.99, null, 'poblano peppers,queso fresco,flour,eggs,vegetable oil,tomato sauce', 'milk,wheat', 2, 2, 2),
('Cochinita Pibil', 'Slow-roasted pork dish from the Yucatán Peninsula', 14.99, null, 'pork,achiote paste,orange juice,garlic,cumin,allspice,cilantro,rice,beans', null, 2, 2, 2),
('Horchata', 'Sweetened rice milk beverage', 3.99, null, 'rice,water,cinnamon,vanilla extract,white sugar', null, 2, 2, 2),
('Mexican Hot Chocolate', 'Hot chocolate with cinnamon and chili', 3.99, null, 'milk,cocoa powder,sugar,cinnamon,chili powder', 'milk', 2, 2, 2);


-- Continue items.sql for Italian Menu. 20 items

INSERT INTO items(name, description, price, image, ingredients, allergens, menu_id, restaurant_id, owner_id)
VALUES
('Margherita Pizza', 'Classic Italian pizza with tomatoes, mozzarella and basil', 9.99, null, 'pizza dough,tomatoes,mozzarella,basil,olive oil', 'milk,wheat', 3, 3, 3),
('Pasta Carbonara', 'Rich and creamy pasta dish with pancetta and egg', 11.99, null, 'spaghetti,pancetta,eggs,parmesan,black pepper', 'milk,wheat,eggs', 3, 3, 3),
('Osso Buco', 'Braised veal shanks cooked with vegetables, white wine and broth', 18.99, null, 'veal shanks,carrots,onions,celery,tomatoes,white wine,chicken broth', null, 3, 3, 3),
('Risotto', 'Creamy Italian rice dish', 10.99, null, 'arborio rice,onions,white wine,chicken broth,butter,parmesan', 'milk', 3, 3, 3),
('Tiramisu', 'Coffee-flavored Italian dessert', 7.99, null, 'ladyfingers,espresso,mascarpone,eggs,sugar,cocoa powder', 'milk,wheat,eggs', 3, 3, 3),
('Gelato', 'Italian style ice cream', 4.99, null, 'milk,sugar,egg yolks,flavorings (e.g., vanilla, chocolate, fruit)', 'milk,eggs', 3, 3, 3),
('Focaccia', 'Olive oil-infused Italian bread', 5.99, null, 'flour,yeast,olive oil,rosemary,salt', 'wheat', 3, 3, 3),
('Caprese Salad', 'Simple Italian salad with tomatoes, mozzarella and basil', 7.99, null, 'tomatoes,mozzarella,basil,olive oil,balsamic vinegar', 'milk', 3, 3, 3),
('Minestrone Soup', 'Hearty Italian vegetable soup', 6.99, null, 'onions,celery,carrots,tomatoes,potatoes,beans,spinach,pasta,vegetable broth', 'wheat', 3, 3, 3),
('Cannoli', 'Sicilian pastry filled with a sweet, creamy filling', 6.99, null, 'flour,sugar,cocoa powder,butter,egg whites,marsala wine,ricotta,chocolate chips', 'milk,wheat,eggs', 3, 3, 3),
('Limoncello', 'Italian lemon liqueur', 5.99, null, 'lemon zest,alcohol,sugar,water', null, 3, 3, 3),
('Chianti', 'Italian red wine', 6.99, null, 'grapes (Sangiovese)', null, 3, 3, 3),
('Prosecco', 'Italian sparkling wine', 7.99, null, 'grapes (Glera)', null, 3, 3, 3),
('Espresso', 'Italian coffee drink', 2.99, null, 'coffee beans,water', null, 3, 3, 3),
('Aperol Spritz', 'Italian wine-based cocktail', 8.99, null, 'Prosecco,Aperol,soda water', null, 3, 3, 3),
('Pesto Pasta', 'Pasta with traditional basil pesto', 12.99, null, 'pasta,basil,garlic,pine nuts,parmesan,olive oil', 'milk,wheat', 3, 3, 3),
('Bruschetta', 'Grilled bread with fresh tomatoes and garlic', 6.99, null, 'bread,tomatoes,garlic,basil,olive oil', 'wheat', 3, 3, 3),
('Zabaglione', 'Light, foamy dessert of egg yolks, sugar and wine', 7.99, null, 'egg yolks,sugar,Marsala wine', 'eggs', 3, 3, 3),
('Limoncello Cake', 'Lemon-flavored cake with a limoncello glaze', 8.99, null, 'flour,sugar,butter,eggs,lemon,limoncello,icing sugar', 'milk,wheat,eggs', 3, 3, 3),
('Negroni', 'Italian cocktail with gin, vermouth and Campari', 9.99, null, 'gin,red vermouth,Campari', null, 3, 3, 3);

-- Continue items.sql for French Menu. 20 items

INSERT INTO items(name, description, price, image, ingredients, allergens, menu_id, restaurant_id, owner_id)
VALUES
('Croissant', 'Buttery, flaky pastry', 3.99, null, 'flour,butter,yeast,salt,sugar,milk', 'milk,wheat', 4, 4, 4),
('Ratatouille', 'Vegetable stew', 9.99, null, 'eggplant,zucchini,red bell pepper,tomato,onion,garlic,olive oil,herbes de Provence', null, 4, 4, 4),
('Coq au Vin', 'Chicken braised in wine', 14.99, null, 'chicken,red wine,onion,garlic,bacon,mushrooms,thyme,butter', 'milk', 4, 4, 4),
('Bouillabaisse', 'Provençal fish stew', 17.99, null, 'fish,shellfish,tomatoes,fennel,garlic,saffron,orange zest', 'fish,shellfish', 4, 4, 4),
('Quiche Lorraine', 'Savory pie with bacon and cheese', 10.99, null, 'pie crust,eggs,heavy cream,bacon,gruyere cheese', 'milk,wheat,eggs', 4, 4, 4),
('Crème Brûlée', 'Vanilla custard with a caramelized sugar topping', 6.99, null, 'heavy cream,vanilla bean,sugar,egg yolks', 'milk,eggs', 4, 4, 4),
('Escargot', 'Snails cooked in garlic-parsley butter', 12.99, null, 'snails,garlic,parsley,butter', 'milk', 4, 4, 4),
('Salade Niçoise', 'Salad with tuna, hard-boiled eggs, and anchovies', 12.99, null, 'lettuce,tomatoes,hard-boiled eggs,anchovies,tuna,olives,green beans', 'fish', 4, 4, 4),
('Pâté', 'Spreadable meat paste', 8.99, null, 'pork liver,pork fat,onions,garlic,cognac,herbs', null, 4, 4, 4),
('Tarte Tatin', 'Caramelized apple tart', 7.99, null, 'apples,sugar,butter,puff pastry', 'milk,wheat', 4, 4, 4),
('Champagne', 'Sparkling wine', 9.99, null, 'grapes (Chardonnay, Pinot Noir, Pinot Meunier)', null, 4, 4, 4),
('Bordeaux Wine', 'Red wine from Bordeaux', 7.99, null, 'grapes (Merlot, Cabernet Sauvignon)', null, 4, 4, 4),
('Café au Lait', 'Coffee with milk', 3.99, null, 'coffee,milk', 'milk', 4, 4, 4),
('Kir Royale', 'Cocktail with crème de cassis and champagne', 8.99, null, 'crème de cassis,champagne', null, 4, 4, 4),
('Cognac', 'Variety of brandy', 8.99, null, 'grapes', null, 4, 4, 4),
('Crepes', 'Thin pancakes with a variety of fillings', 8.99, null, 'flour,milk,eggs,butter,sugar,fillings (e.g., Nutella, jam, lemon sugar)', 'milk,wheat,eggs', 4, 4, 4),
('Pissaladiere', 'Pizza-like dish with onions, olives and anchovies', 9.99, null, 'pizza dough,onions,anchovies,black olives,thyme', 'fish,wheat', 4, 4, 4),
('Cassoulet', 'Slow-cooked casserole with meat and white beans', 14.99, null, 'white beans,pork,sausages,duck or lamb,garlic,tomatoes,white wine', null, 4, 4, 4),
('Pastis', 'Anise-flavored spirit', 6.99, null, 'pastis water', null, 4, 4, 4),
('Chateauneuf-du-Pape Wine', 'Red wine from the Rhône wine region', 10.99, null, 'grapes (Grenache, Syrah, Mourvèdre)', null, 4, 4, 4);

-- Continue items.sql for American Menu. 20 items

INSERT INTO items(name, description, price, image, ingredients, allergens, menu_id, restaurant_id, owner_id)
VALUES
('Hamburger', 'Classic American beef burger', 9.99, null, 'beef patty,burger bun,lettuce,tomato,onion,pickles,cheddar cheese', 'milk,wheat', 5, 5, 5),
('Hot Dog', 'Grilled or steamed sausage served in the slit of a partially sliced bun', 4.99, null, 'sausage,hot dog bun,ketchup,mustard,onions,relish', 'wheat', 5, 5, 5),
('BBQ Ribs', 'Pork ribs cooked with a BBQ sauce', 15.99, null, 'pork ribs,BBQ sauce', null, 5, 5, 5),
('Fried Chicken', 'Chicken pieces breaded and deep-fried', 12.99, null, 'chicken,flour,garlic powder,paprika,black pepper,salt,vegetable oil', 'wheat', 5, 5, 5),
('Mac and Cheese', 'Macaroni pasta with cheese sauce', 8.99, null, 'macaroni,cheddar cheese,milk,butter,flour', 'milk,wheat', 5, 5, 5),
('Clam Chowder', 'Creamy soup with clams and potatoes', 7.99, null, 'clams,potatoes,onions,celery,bacon,cream', 'milk,shellfish', 5, 5, 5),
('Apple Pie', 'Classic dessert with sweet apple filling', 5.99, null, 'apples,sugar,cinnamon,nutmeg,lemon juice,pie crust', 'wheat', 5, 5, 5),
('Cheesecake', 'Sweet dessert with a mixture of soft cheese, eggs, and sugar', 6.99, null, 'cream cheese,eggs,sugar,vanilla extract,graham cracker crumbs,butter', 'milk,wheat,eggs', 5, 5, 5),
('Cobb Salad', 'Salad with chicken, bacon, eggs, tomatoes, and avocado', 10.99, null, 'lettuce,chicken,bacon,hard-boiled eggs,tomatoes,avocado,blue cheese,red-wine vinaigrette', 'milk,eggs', 5, 5, 5),
('Buffalo Wings', 'Chicken wings covered in spicy sauce', 9.99, null, 'chicken wings,hot sauce,butter,blue cheese dressing,celery', 'milk', 5, 5, 5),
('Coca-Cola', 'Popular carbonated soft drink', 2.99, null, 'carbonated water,high fructose corn syrup,caramel color,phosphoric acid,natural flavors,caffeine', null, 5, 5, 5),
('Iced Tea', 'Cold tea beverage sweetened with sugar', 2.99, null, 'tea,sugar,water,lemon', null, 5, 5, 5),
('Root Beer', 'Sweet carbonated beverage', 2.99, null, 'carbonated water,sugar,natural and artificial flavors,caramel color,sodium benzoate', null, 5, 5, 5),
('Milkshake', 'Cold dessert drink made from milk, ice cream, and flavorings', 4.99, null, 'milk,vanilla ice cream,flavoring (chocolate, strawberry, etc)', 'milk', 5, 5, 5),
('Craft Beer', 'Artisanal beer', 5.99, null, 'water,malted grains (barley, wheat),yeast,hops', 'wheat', 5, 5, 5),
('Grilled Cheese Sandwich', 'Toasted sandwich with melted cheese', 6.99, null, 'bread,cheddar cheese,butter', 'milk,wheat', 5, 5, 5),
('Clam Bake', 'Seafood dish with clams, corn, and potatoes', 14.99, null, 'clams,corn,potatoes,lobster,lemon,garlic,butter', 'shellfish,milk', 5, 5, 5),
('Biscuits and Gravy', 'Soft biscuits covered in white gravy', 7.99, null, 'flour,baking powder,milk,butter,salt,sausage,white pepper', 'milk,wheat', 5, 5, 5),
('Pecan Pie', 'Pie with a sweet filling of corn syrup and pecans', 6.99, null, 'pie crust,corn syrup,sugar,pecans,eggs,butter,vanilla extract', 'milk,wheat,eggs,tree nuts', 5, 5, 5),
('Pumpkin Spice Latte', 'Coffee drink with pumpkin and fall spices', 4.99, null, 'coffee,milk,pumpkin puree,sugar,pumpkin spice,vanilla extract,whipped cream', 'milk', 5, 5, 5);