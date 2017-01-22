# Simple Slider

I wrote this library just because I want a lightweight slider.
>Use your own imageloader;  
>Create your own indicator;  
>Variety of transition effects;  
>Customize the animation of each page.

~(～￣▽￣)～

## Demo

[中文文档](http://www.cpacm.net/2016/06/03/%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE%EF%BC%9A%E4%B8%80%E4%B8%AA%E9%AB%98%E5%BA%A6%E8%87%AA%E7%94%B1%E7%9A%84%E8%BD%BB%E9%87%8F%E5%8C%96Slider/)

![simpleslider](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/simpleslider.png)
 
## How to use

### Gradle

```groovy
dependencies {
    	compile 'net.cpacm.simpleslider:library:1.0.4'
}
```

### Add the slider to your layout
```xml
<net.cpacm.library.SimpleSliderLayout
    android:id="@+id/simple_slider"
    android:layout_width="match_parent"
    android:layout_height="140dp"
    android:background="#ffffff">
    
</net.cpacm.library.SimpleSliderLayout>
```

### Config in your code

```java
simpleSliderLayout.setCycling(true);
simpleSliderLayout.setAutoCycling(true);
simpleSliderLayout.setSliderDuration(3000);
simpleSliderLayout.setSliderTransformDuration(1000);
simpleSliderLayout.setPageTransformer(new FlipPageViewTransformer());
simpleSliderLayout.setAnimationListener(null);
circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
simpleSliderLayout.setViewPagerIndicator(circlePageIndicator);
simpleSliderLayout.setAnimationListener(new DefaultDescriptionAnimation());
```
 
## Advanced usage

### Slider
- `ImageSliderView` include ImageView
- `DescriptionSliderView` include ImageView and TextView

**You can customize the Slider extends `BaseSliderView`**

### Indicator

![indicator](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/simpleslider_indicator.gif)

- `CirclePageIndicator`
- `IconPageIndicator`
- `LinePageIndicator`
- `UnderlinePageIndicator`

The above indicators modified from [ViewPagerIndicator](https://github.com/JakeWharton/ViewPagerIndicator)

- `SpringIndicator`

Modify from [SpringIndicator](https://github.com/chenupt/SpringIndicator)

**You can customize the indicator implements `PageIndicator`**

### Transform
![Transform](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/simpleslider_transform.gif)

The above transforms come from [AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider) 

**You can customize the transform extends `BaseTransformer`**

### Animation
![Transform](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/simpleslider_animation.gif)

**You can customize the animation implements `OnAnimationListener`**

## **Note:**
**In the infinite loop mode, at least three sliders.**
**Android API >= 3.0**

License
---

    Copyright 2016 cpacm

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
