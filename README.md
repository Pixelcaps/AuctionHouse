# AuctionHouse

This is a sample application built for my Mobile Operating Systems class for university. It's purpose is to emulate a store and auction house for various items. The application is integrated with Firebase, specifically the Authentication module and the Cloud Firestore NoSQL database. The app support authentification through email and password, but can be also be extended easily to have any type of social sign in available (including the new Apple one). The Firestore database has one collection to store the items put up for sale. Each item has 6 fields: name, seller, startPrice, expirationDate, highestBidder and currentBid. Also, any change made on the application can be seen in realtime.


The app has 4 screens:

1) Login screen
2) Signup screen
3) Main screen which has a list of item cards. Each card displays the name, end date of the bidding period, start price, current bid, seller name and the name of the highest current bidder. In addition to this information, the card also has a button (looks like a dollar sign) in the right top corner of the card and when a user clicks it, he bids 10 over the current bid or starts the bidding with the same amount as the first price asked by the seller. Subsequently, each bid adds 10 extra to the latest price. If it's past the expiration date selected by the seller, the bidding will stop and the item will appear as EXPIRED (sold to the last highest bidder). The list of items is a RecyclerView and each item is CardView.
4) An add item screen. Any registered user is able to add items to the auction house for sale. 
