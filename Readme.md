# Project 2 - *Image Finder*

**Image Finder** is an android app that allows a user to search for images on web using simple filters. The app utilizes [Google Image Search API](https://developers.google.com/image-search/). Please note that API has been officially deprecated as of May 26, 2011.

Time spent: **9** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for images** by specifying a query and launching a search. Search displays a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Size (small, medium, large, extra-large)
  * [x] Color filter (black, blue, brown, gray, green, etc...) -> does not seem to work
  * [x] Type (faces, photo, clip art, line art) -> does not seem to work
  * [x] Site (espn.com)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any image in results to see the image **full-screen**
* [x] User can **scroll down to see more images**. The maximum number of images is 64 (limited by API).

The following **optional** features are implemented:

* [x] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [x] User can **share an image** to their friends or email it to themselves
* [x] Replaced Filter Settings Activity with a lightweight modal overlay
* [x] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [x] Used the [StaggeredGridView](https://github.com/f-barth/AndroidStaggeredGrid) to display improve the grid of image results
* [x] User can [zoom or pan images](https://github.com/MikeOrtiz/TouchImageView) displayed in full-screen detail view

The following **additional** features are implemented:

* [x] Added shared element transition
* [x] Added a cardview with rounded corners and display of image dimensions
* [x] Saving the results into Parcelable array in Save/Restore instance state when device is rotated instead of re-fetching data.
* [x] Added enter and exit window animation for filter settings dialog.
* [x] Added 'Visit page' icon on the detail page that takes the user to the webpage where the image was found.
* [x] Added a progress bar in the actionbar that is displayed when data is being fetched.


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![imgsearch2.gif](https://github.com/sushmatn/ImageFinder/blob/master/imgsearch2.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

Empty View:

![EmptyView.png](https://github.com/sushmatn/ImageFinder/blob/master/EmptyView.png)

Landscape mode:

![landscape.png](https://github.com/sushmatn/ImageFinder/blob/master/landscape.png)

Portrait mode:

![portrait.png](https://github.com/sushmatn/ImageFinder/blob/master/portrait.png)


## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2015] [Sushma Nayak]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
