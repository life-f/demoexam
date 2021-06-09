package org.company.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculationTest
{
    private Calculation calculation;

    @BeforeEach
    public void init()
    {
        this.calculation = new Calculation();
    }

    @Test
    @DisplayName("EasyTest_01_GetQuantityForProduct_Good_Values")
    public void test01()
    {
        Assertions.assertEquals(2, this.calculation.getQuantityForProduct(1, 1, 1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_01_GetQuantityForProduct_Negative_Product_Type")
    public void test02()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(-1, 1, 1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_02_GetQuantityForProduct_Negative_Material_Type")
    public void test03()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, -1, 1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_03_GetQuantityForProduct_Negative_Count")
    public void test04()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, 1, -1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_05_GetQuantityForProduct_Negative_Width")
    public void test05()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, 1, 1, -1, 1));
    }

    @Test
    @DisplayName("EasyTest_06_GetQuantityForProduct_Negative_Height")
    public void test06()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, 1, 1, 1, -1));
    }

    @Test
    @DisplayName("EasyTest_07_GetQuantityForProduct_Invalid_Product_Type")
    public void test07()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(100, 1, 1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_08_GetQuantityForProduct_Invalid_Material_Type")
    public void test08()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, 100, 1, 1, 1));
    }

    @Test
    @DisplayName("EasyTest_09_GetQuantityForProduct_Good_Values")
    public void test09()
    {
        Assertions.assertEquals(877633, this.calculation.getQuantityForProduct(2, 1, 35, 100, 100));
    }

    @Test
    @DisplayName("EasyTest_10_GetQuantityForProduct_Good_Values")
    public void test10()
    {
        Assertions.assertEquals(17249514, this.calculation.getQuantityForProduct(3, 2, 545, 50, 75));
    }

    @Test
    @DisplayName("HardTest_11_GetQuantityForProduct_Good_Values_With_Float")
    public void test11()
    {
        Assertions.assertEquals(95874, this.calculation.getQuantityForProduct(1, 1, 44, 44.44F, 44.44F));
    }

    @Test
    @DisplayName("HardTest_12_GetQuantityForProduct_Good_Values_With_Float")
    public void test12()
    {
        Assertions.assertEquals(424808, this.calculation.getQuantityForProduct(2, 2, 55, 55.55F, 55.55F));
    }

    @Test
    @DisplayName("HardTest_13_GetQuantityForProduct_Good_Values_With_Float")
    public void test13()
    {
        Assertions.assertEquals(37572, this.calculation.getQuantityForProduct(3, 1, 1, 66.66F, 66.66F));
    }

    @Test
    @DisplayName("HardTest_14_GetQuantityForProduct_Invalid_Values_With_Float")
    public void test14()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(11, 1, -1, 46.3F, 155.5F));
    }

    @Test
    @DisplayName("HardTest_15_GetQuantityForProduct_Invalid_Values_With_Float")
    public void test15()
    {
        Assertions.assertEquals(-1, this.calculation.getQuantityForProduct(1, 11, 144, 33.7F, 10.6F));
    }
}
