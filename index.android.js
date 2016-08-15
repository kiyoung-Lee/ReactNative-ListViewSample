/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, {
  AppRegistry,
  Component,
  Image,
  ListView,
  StyleSheet,
  TouchableNativeFeedback,
  TouchableHighlight,
  Text,
  View
} from 'react-native';

var REQUEST_URL = 'https://s3-ap-northeast-1.amazonaws.com/croquis-zigzag/test/sample/shopping_mall_list.json';
var BOOKMARK_ON_URL = 'https://s3-ap-northeast-1.amazonaws.com/croquis-zigzag/test/sample/bookmark_on.png';
var BOOKMARK_OFF_URL = 'https://s3-ap-northeast-1.amazonaws.com/croquis-zigzag/test/sample/bookmark_off.png';
var bindingList = [];
var bookMarkList = [];
var markupOff = "모두보기";
var markupOn = "즐겨찾기만 보기";
var markupControl = markupOn;

class AwesomeProject extends Component {
  
  constructor(props) {
    super(props);
    this.onPressMarkUpButton = this.onPressMarkUpButton.bind(this);
    this.renderRowItem = this.renderRowItem.bind(this);
    this.quickSort = this.quickSort.bind(this);
    this.state = {
      headerDate : null,
      originDataList: null,
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => (row1 !== row2),
      }),
      loaded: false,
    };
  }

  componentDidMount() {
    this.fetchData();
  }

  fetchData() {
    fetch(REQUEST_URL)
      .then((response) => response.json())
      .then((responseData) => {
        this.state.headerDate = responseData.week;
        this.state.originDataList = responseData.list;
        this.sortOriginData();
        this.setCustomList();
      })
      .then(() =>{
        this.setState({
          dataSource: this.state.dataSource.cloneWithRows(bindingList),
          loaded: true,
        });
      })
      .done();
  }

 setCustomList()
 {
   var array = this.state.originDataList;
   for (var i=0; i< array.length; i++)
   {
     bindingList.push( { index: i + 1, name: array[i].name, url: array[i].url, style: array[i].style, score: array[i].score, isBookMark : false});
   }
 }
 
 sortOriginData()
 {
   var length = this.state.originDataList.length; 
   this.quickSort(this.state.originDataList, 0, length -1);
 }

 quickSort(arr, left, right)
 {
    var i = left;
    var j = right;
    var tmp;
    pivotidx = parseInt((left + right) / 2); 
    var pivot = arr[pivotidx];  
    /* partition */
    while (i <= j)
    {
      while (parseInt(arr[i].score) > pivot.score)
      i++;
      while (parseInt(arr[j].score) < pivot.score)
        j--;
      if (i <= j)
      {
        tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        i++;
        j--;
      }
    }

    /* recursion */
    if (left < j)
      this.quickSort(arr, left, j);
    if (i < right)
      this.quickSort(arr, i, right);
    return arr;
 }

  render() {
    return (
      <View style={styles.mainContainer}>
        <View style={styles.titleBar}><Text style={styles.text}>{this.state.headerDate}</Text></View>
        <TouchableNativeFeedback onPress={ () => this.onPressMarkupListControl()}>
          <View style={styles.markupBar}><Text style={styles.text}>{markupControl}</Text></View>
        </TouchableNativeFeedback>
        
        <ListView
          dataSource={this.state.dataSource}
          renderRow={this.renderRowItem}
          style={styles.listView}
          />
      </View>
    );
  }

  onPressMarkupListControl() {
    if(markupControl == markupOff)
    {
      markupControl = markupOn;
      this.setState({ 
      dataSource: this.state.dataSource.cloneWithRows(bindingList), });
    }
    else if(markupControl == markupOn)
    {
      markupControl = markupOff;
      var newBookMarList = [];
      bindingList.forEach(function(element) {
        if(element.isBookMark)
        {
          newBookMarList.push(element);
        }
      }, this);
      bookMarkList = newBookMarList;
      this.setState({ 
      dataSource: this.state.dataSource.cloneWithRows(newBookMarList), });
    }
  }
  
  onPressMarkUpButton(rowItem){
    rowItem.isBookMark = !rowItem.isBookMark;
    if(markupControl == markupOff)
    {
      var newBookMarList = [];
      bookMarkList.forEach(function(element) {
        if(element.isBookMark)
        {
          newBookMarList.push(element);
        }
      }, this);
      bookMarkList = newBookMarList;
    
      this.setState({ 
      dataSource: this.state.dataSource.cloneWithRows(newBookMarList), });
    }
    else if(markupControl == markupOn)
    {
      var cloneData = bindingList.slice();
      cloneData[rowItem.index -1] = 
      {
        index: cloneData[rowItem.index - 1].index,
        name: cloneData[rowItem.index - 1].name,
        url: cloneData[rowItem.index - 1].url,
        style: cloneData[rowItem.index - 1].style,
        score: cloneData[rowItem.index - 1].score,
        isBookMark: cloneData[rowItem.index - 1].isBookMark
      };
      
      bindingList = cloneData;
      this.setState({ 
      dataSource: this.state.dataSource.cloneWithRows(cloneData), });
    }
  }

  renderRowItem(rowItem) {
    var bookMarkURL = '';
    
    if(rowItem.isBookMark)
    {
      bookMarkURL = BOOKMARK_ON_URL;
    }
    else if(rowItem.isBookMark == false)
    {
      bookMarkURL = BOOKMARK_OFF_URL
    }
    return (
      <View style={styles.mainContainer}>
        <View style={styles.rowContainer}>
          <View style={styles.indexContainer}>
            <Text style={styles.title}>{rowItem.index}</Text>
          </View>
          <View style={styles.imageContainer}>
            <Image
              source={{uri: 'https://s3-ap-northeast-1.amazonaws.com/croquis-zigzag/test/sample/images/' + rowItem.url.split('.')[1] + '.jpg'}}
              style={styles.thumbnail}
            />
          </View>
          <View style={styles.contentsContainer}>
            <Text style={styles.title}>{rowItem.name}</Text>
            <View style = {styles.codiStyleContainer}>
              <Text style={styles.codiStyle}>{rowItem.style}</Text>
            </View>
          </View>
            <View style={styles.bookmarkContainer}>
              <TouchableHighlight onPress={ () => this.onPressMarkUpButton(rowItem)}>
                <Image 
                      source={{uri: bookMarkURL}}
                      style={styles.bookmark}
                    />
              </TouchableHighlight>
            </View>
        </View>
        <View style = {styles.separator}/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
  rowContainer: {
    flex: 1,
    marginTop : 5,
    marginBottom: 5,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  separator: {
    height: 1,
    backgroundColor: '#dddddd'
  },
  indexContainer:
  {
    flex : 0.3,
    justifyContent: 'center',
    alignItems: 'center',
  },
  imageContainer:
  {
    flex : 0.4,
  },  
  contentsContainer: {
    flex : 1,
  },
  bookmarkContainer: {
    flex: 0.3,
  },
  title: {
    fontSize: 15,
    marginBottom: 5,
    textAlign: 'left',
  },
  codiStyleContainer: {
    flex : 1,
    flexDirection: 'row',
  },
  codiStyle: {
    fontSize: 10,
    textAlign : 'left',
    backgroundColor: '#32CD32',
  },
  thumbnail: {
    width: 45,
    height: 45,
    borderRadius: 100
  },
  bookmark: {
    width : 45,
    height : 45,
  },
  listView: {
    paddingTop: 20,
    backgroundColor: '#F5FCFF',
  },
  titleBar: {
    height: 40,
    justifyContent: 'center',
    alignItems: 'flex-start',
    backgroundColor: '#9999',
    paddingLeft: 20,
  },
  markupBar: {
    backgroundColor: '#FF69B4',
  },
});

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);
