package Pages;

import Base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {
    @AndroidFindBy(id = "checkStatus") private MobileElement productTitleTxt;

    public String getTitle()
    {
        return getAttribute(productTitleTxt,"text");
    }

}
