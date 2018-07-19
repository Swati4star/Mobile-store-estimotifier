# Airtel App Development Contest Submission

One [Estimote beacon](http://estimote.com/) in each Airtel store and with that users can get numerous facilities. The data related to that specific airtel store, along with the Estimote beacon ID, are uploaded on the cloud MySQL database.

+ **[Estimote Beacons](#estimote-beacons)**
+ **[Features](#features)**
  + [Notify User as soon as he enters proximity](#1-notify-user-as-soon-as-he-enters-proximity)
  + [Display Services](#2-display-services)
  + [Locate nearby Airtel Stores](#3-locate-nearby-airtel-stores)
  + [Recommend best Airtel plans](#4-recommend-best-airtel-plans)
  + [Chat Serices](#5-chat-services)
  + [Other Services](#6-other-services)
+ **[Technical Requirements](#technical-requirements)**
+ **[Video Link](https://www.youtube.com/watch?v=QZUsQFbLwn4)**
+ **[Presentation](https://docs.google.com/presentation/d/1gXRQZ-Zp1z8lc9oOKbzXLmE9rUBiJ5aX8jM3oUq7ihA/edit?usp=sharing)**
+ **[How does Estitmote Beacon work](#how-does-estitmote-beacon-work)**
+ **[Contact Us](#contact-us)**

## Demo Video on YouTube

[![Mobile Store Estmotifier Youtube Video](https://img.youtube.com/vi/QZUsQFbLwn4/0.jpg)](https://www.youtube.com/watch?v=QZUsQFbLwn4)



## Estimote Beacons 
Estimote Beacons are small wireless sensors that we can attach to any location or object. They broadcast tiny radio signals which our smartphone can receive and interpret, unlocking micro-location and contextual awareness.

<img src="http://www.mjdinteractive.com/wp-content/uploads/2013/12/estimote-beacons1.jpg" width="400px" height="200px" />

## Features

### 1. Notify User as soon as he enters proximity
As soon as the user with Airtel Android app installed comes in proximity (~70m) of the store (detected by estimote beacon), he receives a push notification welcoming him to the store. This will help us keep a track of total footfall of users in each store.

<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/notifi.png" width="200px" height="350px" /> <img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-59-18.png" width="200px" height="350px" />


For every new user, we automatically update his information (Mobile Number, Service provider, etc.) to the server database.

To encourage people to come to the store, for every 100th user for each store with Airtel number, we offer him a special prize (some recharge coupons or additional 2G/3G data).

<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2016-01-14-21-11-28_hackathon.airtel.png" width="200px" height="350px" /> <img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-12-43-42.png" width="200px" height="350px" />

### 2. Display Services
As soon as the estimote beacon finds an Airtel app it triggers and phone fetches all the the services offered by that store and are presented to the user. For the services which require personnel assistance an automatically generated **token number** and the estimated time left is shown. The key benefit of adding this is that user does not need to wait in the queue, he/she gets everything at one go.


<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-31-25.png" width="200px" height="350px" /> <img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-58-45.png" width="200px" height="350px" /> <img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-31-22.png" width="200px" height="350px" />

### 3. Locate nearby Airtel Stores
If the user needs to visit an Airtel Store, we provide him with the information related to all the nearby Airtel stores. This can be further extended to provide store in those areas from where we get many requests but there is no store in their locality.

<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-12-43-16.png" width="200px" height="350px" />



### 4. Recommend best Airtel plans
Like Airtel provides the user with **[Airtel Special 5 Offer](http://www.airtel.in/mobile/prepaid/services?CIRCLE=7&CIRCLENAME=Kerala&ID=1212&SERVICENAME=Special%205)**; we help users with Airtel app to see their most frequently contacted numbers **(this is calculated using a proper weighted algorithm incorporating incoming calls, outgoing calls, missed calls and text messages)** with which we can recommend them the best plans related to their usage. This can be similarly extended to data usage.

<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/PhotoGrid_1452786264982.jpg" width="200px" height="350px" />


### 5. Chat Services
There are times when Airtel customer care number is busy. For those "rare" times, we have an online chat platform for live chat with Airtel Customer Care Executive for instant support.

For Airtel personnels, we have designed a platform from which they can resolve all the problems easily because of an easy user interface. For each query, several details like service provider, mobile number, location, etc. are shown for best support.


<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/chat.png" width="200px" height="350px" />



### 6. Other Services
Many users tend to buy devices from Airtel store or get their mobiles recharged. Our app provides the user with various services like purchasing the devices available at that store. Users can even recharge their phone from this app itself. We can provide some coupon-codes for users who use this recharge platform to encourage its usage.


<img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-31-15.png" width="200px" height="350px" /> <img src="https://github.com/Swati4star/Hackathon-airtel/blob/master/screenies/Screenshot_2015-12-12-18-55-07.png" width="200px" height="350px" />


## Technical Requirements

Technically, all Bluetooth Smart-enabled android devices could pick up Bluetooth Low Energy signals. The Bluetooth Special Interest Group maintains a [list of devices](https://www.bluetooth.com/what-is-bluetooth-technology/bluetooth-devices) that support Bluetooth Low Energy. These include Android devices like: Samsung Galaxy S devices, Google Nexus, Google Glass and many more.

## How does Estitmote Beacon work

![](/screenies/Screenshot%20from%202016-01-17%2001:58:26.png)


## Contact Us

Feel free to contact us for any support, query, suggestion or even say hi.

**[Prabhakar Gupta](mailto:prabhakargupta267@gmail.com)**

**[Swati Garg](mailto:swati.garg.nsit@gmail.com)**
