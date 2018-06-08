# Simple Slider

I wrote this library just because I want a lightweight slider.
>Use your own imageloader;  
>Create your own indicator;  
>Variety of transition effects.

这是一款轻量级的图片轮播器，它包括了一下几个特点：

>与图片加载框架完全解耦;  
>内置常用的指示器，基本满足对指示器的需求；  
>丰富多彩的转场动画，亦可以发挥你的创意创造新的特效；  
>内置不同款式的轮播器。

 
## How to use

### Gradle

```groovy
dependencies {
    implementation 'com.cpacm.simpleslider:library:2.2.1'
}

if module has conflict, use
如果有组件冲突，可以使用以下方法导入
dependencies {
    implementation ('com.cpacm.simpleslider:library:2.2.1'){
        exclude module: 'appcompat-v7'
    }
}

```

### Add the slider to your layout
```xml
<com.cpacm.library.SimpleViewPager
    android:id="@+id/simple_slider"
    android:layout_width="match_parent"
    android:layout_height="140dp">

</com.cpacm.library.SimpleSlider>
```

### Config in your code

```java
simpleSlider.setInfiniteEnable(true);
simpleSlider.startAutoScroll(true);
simpleSlider.setSliderDuration(3000);
simpleSlider.setSliderTransformDuration(DEFAULT_SCROLL_DURATION, new SpringInterpolator());
simpleSlider.setPageTransformer(new ZoomOutSlideTransformer());
circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
circlePageIndicator.setViewPager(simpleSlider);
//when adapter's data changed, you should call this method
//当adapter数据更新时，必须要调用
simpleSLider.notifyDataSetChanged();

```

## Advanced usage

### Basic
![indicator](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/BasicSlider.gif)

两种轮播器，一种是普通模式，一种是回廊模式。

### Indicator

![indicator](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/IndicatorSlider.gif)

- `CirclePageIndicator`
- `IconPageIndicator`
- `LinePageIndicator`
- `UnderlinePageIndicator`

The above indicators modified from [ViewPagerIndicator](https://github.com/JakeWharton/ViewPagerIndicator)

- `SpringIndicator`

Modify from [SpringIndicator](https://github.com/chenupt/SpringIndicator)

多种样式的指示器，可以根据场景选择适合的样式使用。

**You can customize the indicator implements `PageIndicator`**

### Transform
![Transform](https://raw.githubusercontent.com/cpacm/SimpleSlider/develop/pic/TransformSlider.gif)

The above transforms come from [AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider) 

**You can customize the transform extends `BaseTransformer`**

丰富的过场动画（上面的回廊式轮播就可以通过设置 Transform 来实现）

## **Note:**
无限轮播最低要求有三张页面。

**In the infinite loop mode, at least three sliders.**
**Android API >= 3.0**

License
---

    Copyright 2018 cpacm

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
