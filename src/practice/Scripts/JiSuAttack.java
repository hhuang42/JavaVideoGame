/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

/**
 *
 * @author Henry
 */
import java.awt.Graphics2D;
import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import practice.Basic.BasicScript;
import static practice.Calc.*;

public class JiSuAttack extends BossScript {

    {
    }
    static Col[] despColors= {Col.YELLOW,Col.YELLOW,Col.YELLOW,Col.YELLOW,
        Col.YELLOW,Col.YELLOW,Col.BLUE,Col.BLUE,Col.BLUE,Col.BLUE,Col.BLUE,
        Col.BLUE,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,
        Col.BLACK,Col.GRAY,Col.GRAY,Col.GRAY,Col.WHITE,Col.WHITE,Col.BROWN,
        Col.BROWN,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,
        Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,
        Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.YELLOW,
        Col.WHITE,Col.WHITE,Col.WHITE,Col.WHITE,Col.WHITE,Col.WHITE,Col.WHITE};
    static Col[] dugColors = {Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,
        Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,
        Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BROWN,Col.BLACK,
        Col.BLACK,Col.BLACK,Col.BROWN,Col.WHITE,Col.WHITE,Col.WHITE,Col.WHITE,
        Col.WHITE,Col.WHITE,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,
        Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.BLACK,Col.RED,Col.WHITE,
        Col.WHITE,Col.BROWN,Col.BROWN,Col.BLACK,Col.BLACK};
    static double[][][] despCoord=getDespCoord();
    static double[][][] dugCoord=getDugCoord();

    double[][][] useCoord;
    Col[] useColor;
    int moveDistance;
    int breakTime;
    int sign;
    int pointPeriod;
    int linePeriod;
    int drawPeriod;
    int direction;
    float speedMult;
    float height;
    public static double[][][] getDugCoord(){
    double[][][] dugCoord={{{-0.109, 0.18}, {-0.131, 0.165}, {-0.145, 0.143}, {-0.16, 
   0.122}, {-0.176, 0.101}, {-0.19, 0.079}, {-0.205, 0.057}, {-0.213, 
   0.032}, {-0.229, 
   0.011}, {-0.237, -0.013}, {-0.247, -0.037}, {-0.261, -0.059},
{-0.277, -0.08}, {-0.289, -0.103}, {-0.302, -0.126}, {-0.318,
-0.146}, {-0.338, -0.163}, {-0.345, -0.187}, {-0.346, -0.213}},
{{0.331, 0.026}, {0.332, 
   0.}, {0.334, -0.026}, {0.336, -0.052}, {0.338, -0.078}, {0.339,
-0.104}, {0.337, -0.13}, {0.328, -0.154}, {0.313, -0.175}, {0.296,
-0.195}}, {{0.288, -0.198}, {0.277, -0.222}, {0.27, -0.246}, {0.264,
-0.271}, {0.256, -0.296}, {0.245, -0.32}, {0.23, -0.342}, {0.215,
-0.362}, {0.194, -0.378}, {0.171, -0.391}, {0.148, -0.403}, {0.126,
-0.417}, {0.101, -0.424}, {0.075, -0.428}, {0.049, -0.431}},
{{-0.091, -0.435}, {-0.065, -0.438}}, {{-0.337, -0.238}, {-0.339,
-0.264}, {-0.333, -0.29}, {-0.325, -0.314}, {-0.311, -0.337},
{-0.297, -0.359}, {-0.277, -0.376}}, {{-0.331, -0.238}, {-0.307,
-0.23}, {-0.282, -0.235}, {-0.263, -0.252}, {-0.245, -0.271},
{-0.233, -0.295}, {-0.22, -0.317}, {-0.205, -0.338}, {-0.195,
-0.363}, {-0.188, -0.388}}, {{-0.232, -0.438}, {-0.211, -0.454},
{-0.193, -0.472}, {-0.169, -0.478}, {-0.143, -0.478}, {-0.117,
-0.476}, {-0.099, -0.458}, {-0.1, -0.432}, {-0.116, -0.418}, {-0.14,
-0.407}, {-0.165, -0.399}}, {{-0.14, 0.082}, {-0.156, 0.063}, {-0.157,
    0.037}, {-0.158, 
   0.01}, {-0.158, -0.016}, {-0.157, -0.042}, {-0.155, -0.068},
{-0.153, -0.094}, {-0.152, -0.12}, {-0.15, -0.146}, {-0.144, -0.172},
{-0.137, -0.197}, {-0.13, -0.222}, {-0.122, -0.247}, {-0.115,
-0.272}, {-0.108, -0.298}, {-0.099, -0.322}, {-0.091, -0.347},
{-0.082, -0.372}, {-0.073, -0.396}, {-0.064, -0.42}, {-0.048,
-0.441}}, {{-0.011, -0.248}, {-0.011, -0.274}, {-0.011, -0.3},
{-0.011, -0.326}, {-0.011, -0.352}, {-0.005, -0.377}, {0.004,
-0.402}, {0.02, -0.423}, {0.038, -0.441}, {0.064, -0.444}, {0.078,
-0.465}, {0.078, -0.491}, {0.057, -0.5}, {0.031, -0.5}, {0.005,
-0.5}, {-0.019, -0.489}, {-0.032, -0.469}}, {{-0.34, -0.245},
{-0.359, -0.263}, {-0.382, -0.275}, {-0.407, -0.283}, {-0.432,
-0.291}, {-0.457, -0.296}, {-0.48, -0.307}, {-0.494, -0.328}, {-0.5,
-0.353}, {-0.498, -0.379}, {-0.495, -0.405}, {-0.486, -0.429},
{-0.467, -0.444}, {-0.442, -0.45}, {-0.416, -0.454}, {-0.39, -0.458},
{-0.364, -0.461}, {-0.338, -0.463}, {-0.313, -0.458}, {-0.289,
-0.448}, {-0.266, -0.435}, {-0.24, -0.43}}, {{-0.402, -0.358},
{-0.375, -0.358}, {-0.35, -0.364}, {-0.324, -0.37}, {-0.299, -0.376},
{-0.276, -0.387}, {-0.255, -0.403}}, {{0.282, -0.22}, {0.283,
-0.246}, {0.285, -0.272}, {0.288, -0.298}, {0.294, -0.324}, {0.299,
-0.349}, {0.311, -0.372}}, {{0.245, -0.331}, {0.251, -0.356}, {0.251,
-0.382}, {0.254, -0.408}, {0.259, -0.433}, {0.284, -0.438}, {0.311,
-0.438}, {0.336, -0.434}, {0.352, -0.418}, {0.346, -0.393}, {0.325,
-0.38}}, {{0.235, -0.343}, {0.241, -0.369}, {0.227, -0.383}, {0.201,
-0.385}}, {{0.131, 0.497}, {0.105, 0.491}, {0.082, 0.48}, {0.059, 
   0.467}, {0.037, 0.452}, {0.015, 0.439}, {-0.01, 0.43}, {-0.028, 
   0.412}, {-0.041, 0.389}, {-0.05, 0.365}, {-0.058, 0.34}, {-0.069, 
   0.316}, {-0.082, 0.295}, {-0.082, 0.269}, {-0.082, 0.243}}, {{0.14,
    0.497}, {0.166, 0.503}, {0.191, 0.496}, {0.216, 0.49}, {0.241, 
   0.481}, {0.263, 0.468}, {0.283, 0.451}, {0.299, 0.431}, {0.314, 
   0.409}, {0.329, 0.387}, {0.343, 0.365}}, {{0.014, 0.395}, {0.009, 
   0.37}, {0.006, 0.344}, {0., 0.318}, {-0.007, 0.293}, {-0.013, 
   0.268}, {-0.017, 0.242}, {-0.024, 0.217}}, {{0.229, 0.463}, {0.244,
    0.442}, {0.264, 0.424}, {0.283, 0.406}, {0.301, 0.388}, {0.318, 
   0.368}}, {{0.155, 0.291}, {0.173, 0.31}, {0.193, 0.327}, {0.216, 
   0.338}, {0.241, 0.346}, {0.266, 0.354}, {0.292, 0.358}, {0.318, 
   0.358}, {0.344, 0.358}, {0.369, 0.354}, {0.395, 0.346}}, {{0.402, 
   0.34}, {0.412, 0.316}, {0.419, 0.291}, {0.416, 0.265}}, {{0.155, 
   0.288}, {0.155, 0.262}, {0.158, 0.236}, {0.171, 0.214}, {0.192, 
   0.197}, {0.214, 0.185}, {0.24, 0.183}, {0.266, 0.179}, {0.292, 
   0.178}, {0.317, 0.182}, {0.342, 0.192}, {0.365, 0.204}, {0.386, 
   0.219}, {0.402, 0.239}}, {{0.402, 0.349}, {0.421, 0.332}, {0.433, 
   0.31}, {0.439, 0.284}, {0.443, 0.259}, {0.434, 0.235}}, {{0.094, 
   0.648}, {0.068, 0.644}, {0.042, 0.64}, {0.017, 0.634}, {-0.008, 
   0.625}, {-0.031, 0.613}, {-0.055, 0.602}, {-0.077, 0.589}, {-0.098,
    0.572}, {-0.118, 0.556}, {-0.137, 0.538}, {-0.151, 
   0.516}, {-0.164, 0.494}, {-0.174, 0.469}, {-0.183, 0.445}, {-0.183,
    0.419}, {-0.183, 0.393}, {-0.179, 0.367}, {-0.174, 
   0.341}}, {{0.097, 0.651}, {0.123, 0.651}, {0.149, 0.652}, {0.175, 
   0.653}, {0.2, 0.646}, {0.226, 0.639}, {0.251, 0.632}, {0.276, 
   0.625}, {0.301, 0.617}, {0.324, 0.605}, {0.347, 0.593}, {0.37, 
   0.579}, {0.391, 0.564}, {0.412, 0.548}, {0.431, 0.53}, {0.446, 
   0.509}, {0.462, 0.489}, {0.473, 0.465}, {0.484, 0.441}, {0.494, 
   0.417}, {0.498, 0.391}, {0.499, 0.365}, {0.5, 0.339}, {0.493, 
   0.314}}, {{-0.168, 0.334}, {-0.154, 0.312}, {-0.14, 0.29}, {-0.124,
    0.269}, {-0.107, 0.249}, {-0.086, 0.233}, {-0.063, 
   0.221}, {-0.041, 0.207}, {-0.019, 0.194}, {0.006, 0.184}, {0.03, 
   0.174}, {0.054, 0.164}, {0.079, 0.157}, {0.105, 0.154}, {0.131, 
   0.152}, {0.157, 0.147}, {0.183, 0.146}, {0.207, 0.143}}, {{0.485, 
   0.288}, {0.473, 0.264}, {0.457, 0.244}, {0.438, 0.226}, {0.419, 
   0.207}, {0.397, 0.194}, {0.375, 0.18}, {0.352, 0.167}, {0.328, 
   0.158}, {0.303, 0.15}, {0.278, 0.141}, {0.252, 0.136}, {0.226, 
   0.134}, {0.2, 0.134}, {0.174, 0.135}, {0.148, 0.138}, {0.122, 
   0.139}, {0.096, 0.142}}, {{-0.168, 0.328}, {-0.156, 
   0.305}, {-0.143, 0.281}, {-0.131, 0.258}, {-0.117, 0.236}, {-0.104,
    0.214}, {-0.091, 0.191}, {-0.078, 0.168}, {-0.066, 
   0.145}, {-0.047, 0.127}, {-0.027, 0.111}, {-0.005, 0.096}, {0.017, 
   0.082}, {0.04, 0.07}, {0.064, 0.058}, {0.088, 0.05}, {0.114, 
   0.043}, {0.14, 0.04}, {0.166, 0.038}, {0.192, 0.041}, {0.217, 
   0.048}, {0.242, 0.055}, {0.264, 0.068}, {0.284, 0.084}, {0.305, 
   0.1}, {0.326, 0.116}, {0.345, 0.134}, {0.363, 0.153}, {0.382, 
   0.171}, {0.402, 0.188}, {0.422, 0.204}}, {{0.082, 0.14}, {0.082, 
   0.114}, {0.079, 0.088}, {0.078, 0.062}}, {{-0.103, 0.208}, {-0.104,
    0.182}, {-0.107, 0.156}}, {{-0.115, 0.134}, {-0.103, 
   0.111}, {-0.089, 0.089}, {-0.075, 0.066}, {-0.05, 0.061}, {-0.027, 
   0.048}, {-0.005, 0.033}, {0.016, 0.019}, {0.041, 0.011}, {0.066, 
   0.004}, {0.092, -0.004}, {0.117, -0.007}, {0.143, -0.005}, {0.169,
-0.002}, {0.195, 0.003}, {0.22, 0.011}, {0.245, 0.016}, {0.27, 
   0.024}, {0.293, 0.038}}, {{0.337, 0.122}, {0.329, 0.096}}, {{0.312,
    0.103}}, {{0.288, 0.088}, {0.277, 0.064}, {0.285, 
   0.041}}, {{0.291, 0.048}, {0.314, 0.036}, {0.339, 0.038}, {0.349, 
   0.061}, {0.334, 0.081}, {0.309, 0.088}, {0.29, 0.072}}, {{0.294, 
   0.038}}, {{-0.072, 0.072}, {-0.065, 0.098}, {-0.058, 
   0.123}}, {{-0.048, 0.057}, {-0.039, 0.082}, {-0.03, 
   0.106}}, {{0.035, 0.035}, {0.055, 0.019}, {0.079, 0.019}, {0.094, 
   0.034}, {0.075, 0.052}, {0.05, 0.057}}, {{0.18, 0.038}, {0.19, 
   0.02}, {0.212, 0.028}}, {{0.152, 0.402}, {0.148, 0.427}, {0.127, 
   0.442}, {0.106, 0.432}, {0.1, 0.408}, {0.116, 0.389}, {0.14, 
   0.396}}, {{0.195, 0.405}, {0.22, 0.408}, {0.229, 0.431}, {0.211, 
   0.448}, {0.188, 0.447}, {0.192, 0.422}}, {{0.109, 0.432}, {0.132, 
   0.427}, {0.126, 0.404}, {0.103, 0.409}}, {{0.192, 0.411}, {0.208, 
   0.428}, {0.194, 0.445}}, {{0.118, 0.414}, {0.112, 0.414}}, {{0.189,
    0.423}}};
            return dugCoord;
            }
    public static double[][][] getDespCoord(){
        double[][][] newList = {{{-0.399, -0.129}, {-0.425, -0.115}, {-0.432, -0.099}, {-0.41,
-0.081}, {-0.383, -0.086}, {-0.366, -0.11}}, {{0.179, 0.53}, {0.163, 
   0.555}, {0.147, 0.579}, {0.125, 0.599}, {0.103, 0.619}, {0.079, 
   0.636}, {0.054, 0.651}, {0.026, 0.662}, {-0.002, 0.672}, {-0.03, 
   0.679}, {-0.059, 0.686}, {-0.089, 0.688}, {-0.118, 0.69}, {-0.147, 
   0.685}, {-0.177, 0.68}, {-0.205, 0.672}, {-0.233, 0.662}, {-0.26, 
   0.651}, {-0.286, 0.636}, {-0.311, 0.62}, {-0.329, 0.597}, {-0.348, 
   0.574}, {-0.366, 0.551}, {-0.383, 0.527}, {-0.396, 0.501}, {-0.404,
    0.472}, {-0.41, 0.443}}, {{-0.39, 0.331}, {-0.386, 
   0.302}, {-0.382, 0.273}, {-0.377, 0.243}, {-0.373, 0.214}, {-0.369,
    0.185}, {-0.367, 0.155}, {-0.365, 0.126}, {-0.365, 
   0.096}}, {{0.217, 0.399}, {0.223, 0.37}, {0.229, 0.341}, {0.232, 
   0.311}, {0.234, 0.282}, {0.238, 0.252}, {0.242, 0.223}, {0.246, 
   0.194}, {0.25, 0.164}, {0.257, 0.181}, {0.264, 0.21}, {0.271, 
   0.238}, {0.278, 0.267}, {0.283, 0.296}, {0.288, 0.326}, {0.291, 
   0.355}, {0.294, 0.384}}, {{0.357, 0.395}, {0.354, 0.365}, {0.352, 
   0.335}, {0.348, 0.306}, {0.344, 0.277}, {0.34, 0.247}, {0.337, 
   0.218}, {0.331, 0.189}, {0.322, 0.161}, {0.315, 0.132}, {0.308, 
   0.103}, {0.302, 0.074}, {0.298, 0.045}, {0.294, 
   0.016}, {0.292, -0.014}, {0.29, -0.043}, {0.289, -0.073}, {0.289,
-0.103}, {0.286, -0.132}, {0.277, -0.16}}, {{-0.378, 0.032}, {-0.399, 
   0.011}, {-0.423, -0.006}, {-0.445, -0.025}, {-0.463, -0.048},
{-0.482, -0.072}, {-0.497, -0.096}, {-0.496, -0.124}, {-0.474,
-0.144}, {-0.45, -0.161}, {-0.426, -0.178}}, {{-0.378, -0.167},
{-0.38, -0.196}, {-0.376, -0.224}, {-0.362, -0.25}, {-0.344, -0.274},
{-0.324, -0.296}, {-0.299, -0.311}, {-0.273, -0.326}, {-0.249,
-0.343}, {-0.23, -0.365}, {-0.222, -0.392}, {-0.22, -0.422}},
{{-0.082, -0.432}, {-0.082, -0.403}, {-0.062, -0.383}, {-0.033,
-0.382}, {-0.027, -0.406}}, {{0.112, -0.432}, {0.118, -0.404},
{0.135, -0.38}, {0.16, -0.366}, {0.188, -0.355}, {0.214, -0.341},
{0.237, -0.323}, {0.251, -0.297}, {0.262, -0.27}, {0.274, -0.242},
{0.279, -0.214}, {0.274, -0.185}, {0.253, -0.17}, {0.225, -0.162},
{0.211, -0.141}, {0.207, -0.112}, {0.203, -0.082}, {0.2, -0.053},
{0.196, -0.024}, {0.192, 0.006}, {0.212, 0.028}, {0.231, 0.05}, {0.24,
    0.078}, {0.244, 0.107}, {0.245, 0.137}}, {{0.247, 0.162}, {0.231, 
   0.138}, {0.215, 0.113}, {0.198, 0.088}, {0.175, 0.073}, {0.147, 
   0.064}, {0.119, 0.055}, {0.09, 0.046}, {0.062, 0.04}, {0.032, 
   0.04}, {0.003, 0.041}, {-0.027, 0.042}, {-0.057, 0.043}, {-0.086, 
   0.045}, {-0.116, 0.046}, {-0.145, 0.047}, {-0.175, 0.05}, {-0.204, 
   0.056}, {-0.233, 0.061}, {-0.263, 0.061}, {-0.292, 0.064}, {-0.321,
    0.071}, {-0.349, 0.08}}, {{-0.361, 0.082}, {-0.381, 
   0.07}, {-0.378, 0.041}}, {{-0.373, 0.036}, {-0.344, 
   0.033}, {-0.315, 0.027}, {-0.287, 0.018}, {-0.259, 
   0.008}, {-0.234, -0.005}, {-0.234, -0.035}, {-0.233, -0.064},
{-0.23, -0.094}, {-0.232, -0.123}, {-0.235, -0.153}, {-0.258,
-0.162}, {-0.287, -0.165}, {-0.317, -0.167}, {-0.346, -0.167}},
{{-0.352, -0.162}, {-0.359, -0.134}, {-0.38, -0.127}, {-0.4, -0.15},
{-0.412, -0.176}, {-0.416, -0.202}, {-0.42, -0.22}, {-0.428, -0.248},
{-0.413, -0.273}, {-0.385, -0.275}, {-0.356, -0.27}}, {{0.289, 
   0.411}, {0.318, 0.407}, {0.348, 0.403}, {0.377, 0.399}, {0.373, 
   0.427}, {0.371, 0.456}, {0.386, 0.482}, {0.399, 0.508}, {0.418, 
   0.53}, {0.44, 0.55}, {0.456, 0.574}, {0.453, 0.6}, {0.43, 
   0.617}, {0.401, 0.61}, {0.385, 0.587}, {0.369, 0.58}, {0.369, 
   0.609}, {0.357, 0.635}, {0.333, 0.645}, {0.313, 0.627}, {0.31, 
   0.598}, {0.313, 0.569}, {0.308, 0.541}, {0.294, 0.516}, {0.298, 
   0.487}, {0.298, 0.458}, {0.291, 0.43}}, {{0.31, 0.551}, {0.333, 
   0.532}, {0.36, 0.537}, {0.387, 0.528}, {0.394, 0.504}, {0.372, 
   0.487}, {0.343, 0.487}, {0.314, 
   0.487}}, {{-0.091, -0.445}, {-0.085, -0.474}, {-0.098, -0.487},
{-0.127, -0.491}, {-0.156, -0.496}, {-0.185, -0.496}, {-0.215,
-0.498}, {-0.244, -0.5}, {-0.255, -0.477}, {-0.244, -0.452}, {-0.216,
-0.444}, {-0.187, -0.439}, {-0.158, -0.437}, {-0.128, -0.437},
{-0.098, -0.437}}, {{-0.015, -0.441}, {-0.015, -0.471}, {-0.002,
-0.487}, {0.028, -0.488}, {0.057, -0.491}, {0.087, -0.493}, {0.116,
-0.496}, {0.146, -0.496}, {0.167, -0.479}, {0.155, -0.452}, {0.128,
-0.442}, {0.099, -0.437}, {0.069, -0.437}, {0.04, -0.437}, {0.01,
-0.437}}, {{-0.365, 0.462}, {-0.387, 0.443}, {-0.406, 0.42}, {-0.422, 
   0.395}, {-0.424, 0.367}, {-0.416, 0.339}, {-0.401, 0.315}, {-0.384,
    0.307}, {-0.394, 0.335}, {-0.372, 0.354}, {-0.35, 
   0.373}}, {{0.146, 0.538}, {0.173, 0.528}, {0.194, 0.507}, {0.213, 
   0.485}, {0.219, 0.456}, {0.225, 0.428}, {0.22, 0.407}, {0.209, 
   0.434}, {0.187, 0.448}}, {{0.15, 0.454}, {0.148, 0.483}, {0.147, 
   0.513}, {0.142, 0.54}, {0.126, 0.564}, {0.111, 0.589}, {0.088, 
   0.608}, {0.061, 0.62}, {0.033, 0.628}, {0.004, 0.633}, {-0.025, 
   0.629}, {-0.053, 0.62}, {-0.077, 0.603}, {-0.097, 0.582}, {-0.115, 
   0.558}, {-0.138, 0.561}, {-0.163, 0.576}, {-0.191, 0.587}, {-0.219,
    0.596}, {-0.247, 0.591}, {-0.276, 0.583}, {-0.302, 
   0.569}, {-0.324, 0.549}, {-0.342, 0.527}, {-0.351, 0.499}, {-0.357,
    0.47}, {-0.358, 0.441}, {-0.35, 0.412}, {-0.342, 0.384}, {-0.325, 
   0.361}, {-0.302, 0.343}, {-0.275, 0.33}, {-0.248, 0.319}, {-0.219, 
   0.314}, {-0.189, 0.315}, {-0.162, 0.327}, {-0.14, 0.347}, {-0.121, 
   0.369}, {-0.102, 0.392}, {-0.076, 0.383}, {-0.05, 0.369}, {-0.024, 
   0.357}, {0.006, 0.357}, {0.035, 0.361}, {0.062, 0.371}, {0.087, 
   0.388}, {0.11, 0.407}, {0.127, 0.43}, {0.145, 0.449}}, {{-0.12, 
   0.47}, {-0.131, 0.498}, {-0.147, 0.522}, {-0.17, 0.541}, {-0.197, 
   0.553}, {-0.225, 0.557}, {-0.254, 0.551}, {-0.282, 0.54}, {-0.305, 
   0.523}, {-0.322, 0.499}, {-0.327, 0.471}, {-0.322, 0.443}, {-0.311,
    0.415}, {-0.296, 0.391}, {-0.271, 0.374}, {-0.245, 
   0.361}, {-0.217, 0.36}, {-0.188, 0.367}, {-0.161, 0.378}, {-0.14, 
   0.397}, {-0.129, 0.424}}, {{-0.086, 0.466}, {-0.086, 
   0.496}, {-0.083, 0.525}, {-0.073, 0.553}, {-0.056, 0.576}, {-0.03, 
   0.589}, {-0.001, 0.597}, {0.028, 0.594}, {0.055, 0.583}, {0.08, 
   0.568}, {0.098, 0.545}, {0.112, 0.519}, {0.115, 0.49}, {0.108, 
   0.461}, {0.094, 0.436}, {0.074, 0.415}, {0.048, 0.4}, {0.019, 
   0.395}, {-0.01, 0.395}, {-0.039, 0.4}, {-0.057, 0.42}, {-0.078, 
   0.441}}, {{-0.319, 0.449}, {-0.31, 0.478}, {-0.298, 
   0.505}, {-0.279, 0.525}, {-0.251, 0.535}, {-0.222, 0.538}, {-0.193,
    0.536}, {-0.168, 0.522}, {-0.148, 0.5}, {-0.137, 0.475}, {-0.137, 
   0.445}, {-0.149, 0.42}, {-0.172, 0.402}, {-0.199, 0.39}, {-0.228, 
   0.386}, {-0.258, 0.388}, {-0.285, 0.397}, {-0.306, 
   0.418}}, {{-0.078, 0.534}, {-0.054, 0.552}, {-0.031, 
   0.569}, {-0.002, 0.575}, {0.027, 0.576}, {0.054, 0.566}, {0.078, 
   0.549}, {0.096, 0.526}, {0.104, 0.5}, {0.094, 0.473}, {0.076, 
   0.451}, {0.05, 0.436}, {0.023, 0.424}, {-0.006, 0.42}, {-0.035, 
   0.421}, {-0.061, 0.435}}, {{-0.188, 0.462}, {-0.216, 
   0.472}, {-0.242, 0.465}, {-0.254, 0.439}, {-0.24, 0.415}, {-0.214, 
   0.409}, {-0.189, 0.424}, {-0.184, 0.45}}, {{0.006, 0.517}, {0.032, 
   0.504}, {0.046, 0.48}, {0.039, 0.454}, {0.013, 0.445}, {-0.016, 
   0.449}, {-0.027, 0.474}, {-0.024, 0.502}}, {{0.019, 
   0.475}, {-0.001, 0.487}, {0.009, 0.466}}, {{-0.205, 
   0.437}, {-0.226, 0.448}, {-0.214, 0.429}}, {{-0.386, 
   0.508}, {-0.415, 0.51}}, {{-0.348, 0.555}, {-0.371, 
   0.574}, {-0.397, 0.587}}, {{-0.348, 0.568}, {-0.374, 0.581}, {-0.4,
    0.593}, {-0.43, 0.593}, {-0.458, 0.587}}, {{-0.302, 
   0.614}, {-0.322, 0.635}, {-0.347, 0.651}, {-0.376, 
   0.655}}, {{-0.276, 0.618}, {-0.294, 0.642}, {-0.318, 
   0.659}}, {{-0.255, 0.652}, {-0.272, 0.676}}, {{-0.243, 
   0.66}, {-0.256, 0.687}, {-0.272, 0.711}, {-0.296, 0.727}}, {{-0.2, 
   0.669}, {-0.218, 0.693}, {-0.242, 0.709}, {-0.268, 
   0.723}}, {{-0.162, 0.66}, {-0.171, 0.689}}, {{-0.129, 
   0.686}, {-0.132, 0.715}, {-0.131, 0.744}, {-0.123, 
   0.773}}, {{-0.103, 0.69}, {-0.107, 0.719}, {-0.099, 
   0.747}}, {{-0.049, 0.686}, {-0.045, 0.715}, {-0.041, 
   0.744}, {-0.027, 0.77}}, {{-0.006, 0.665}, {-0.003, 0.694}, {0.017,
    0.714}}, {{0.011, 0.673}, {0.024, 0.699}, {0.047, 0.718}, {0.071, 
   0.735}}, {{0.065, 0.648}, {0.085, 0.67}, {0.111, 0.684}, {0.136, 
   0.698}}, {{0.074, 0.635}, {0.094, 0.656}, {0.12, 0.671}}, {{0.124, 
   0.601}, {0.152, 0.611}}, {{-0.209, 0.15}, {-0.179, 0.147}, {-0.15, 
   0.147}, {-0.12, 0.149}, {-0.091, 0.155}, {-0.062, 0.158}, {-0.032, 
   0.159}, {-0.003, 0.162}, {0.027, 0.162}, {0.056, 0.166}, {0.085, 
   0.173}, {0.113, 0.18}, {0.12, 0.166}, {0.111, 0.138}, {0.099, 
   0.111}, {0.077, 0.092}, {0.05, 0.08}, {0.021, 0.073}, {-0.008, 
   0.066}, {-0.037, 0.062}, {-0.066, 0.058}, {-0.096, 0.061}, {-0.125,
    0.067}, {-0.153, 0.075}, {-0.176, 0.092}, {-0.195, 
   0.115}, {-0.212, 0.139}}, {{-0.133, 0.141}, {-0.112, 
   0.124}, {-0.082, 0.122}, {-0.055, 0.126}}, {{-0.044, 
   0.137}, {-0.02, 0.123}, {0.009, 0.122}, {0.034, 0.135}}, {{0.036, 
   0.129}, {0.064, 0.121}, {0.092, 0.13}, {0.104, 0.157}}, {{-0.12, 
   0.129}, {-0.144, 0.111}, {-0.173, 0.105}}, {{0.086, 
   0.124}}, {{-0.108, 0.061}, {-0.08, 0.071}, {-0.051, 
   0.071}}, {{-0.032, 0.065}, {-0.011, 0.084}, {0.019, 0.089}, {0.047,
    0.085}}};
        return newList;
    }
    public void start() {
        
                            height = semiHeight*1.1f;
        sign = 1;
        super.initializeBoss();
        //difficulty = difficulty.NORMAL;
        moveDistance = 10;
        breakTime = 360;
        speedMult = 1;

        switch (difficulty) {
            case LUNATIC:
                direction = 1;
        pointPeriod=1;
        linePeriod = 5;
        drawPeriod = 300;
        
        breakTime = 360;
        useCoord = despCoord;
        useColor = despColors;
                break;
            case HARD:
                direction = 1;
                
                
        useCoord = despCoord;
        useColor = despColors;
        pointPeriod=3;
        linePeriod = 8;
        drawPeriod = 480;
        
        breakTime = 540;
                break;
            case NORMAL:
                
        useCoord = dugCoord;
        useColor = dugColors;
        speedMult = .75f;
                direction = -1;
        pointPeriod=1;
        linePeriod = 5;
        drawPeriod = 270;
        
        breakTime = 330;
        
                            height = semiHeight*1f;
                break;
            case EASY:
                speedMult = .5f;
                direction = -1;
                
        pointPeriod=2;
        linePeriod = 8;
        drawPeriod = 480;
        
        breakTime = 540;
                
        useCoord = dugCoord;
        useColor = dugColors;
        
                            height = semiHeight*1f;
                break;
        }
    }

    public void fill() {
        Action drawFace = new Action() {

            public void start() {
                sign*=-1;

                for (int n=0;n<useCoord.length;n++) {
                    final double[][] doubleSets = useCoord[n];
                    final Col thisCol = useColor[n];
                    final int n0 = n*linePeriod;
                    Bullet.reset0();
                    Bullet.type0(Bullet.Type.Small);
                    Bullet.color0(thisCol);
                    Bullet Blah = new Bullet() {

                        int i = 0;

                            float[] velocity = toV(0,.01f);
                        public void move() {
                            super.move();
                            if(age>n0&&age%pointPeriod==0&&status!=Status.Fade)
                            {
                                
                                float x = semiWidth;
                            float angle = 0;
                            float radius = scrWidth*.7f;
                            float yv = 0;
                            double[] doublePts = doubleSets[i];

                            float[] floatpts = toF(doublePts);
                            float[] position = sum(product(Calc.rotate(floatpts, angle), toV(sign*radius, -radius)), toV(x, height));
                            if (i!=doubleSets.length-1) {
                                float[] nextPosition = sum(product(Calc.rotate(toF(doubleSets[i+1]), angle), toV(sign*radius, -radius)), toV(x, height));
                                velocity = norm(diff(position,nextPosition),direction);
                            }
                            Bullet.reset0();
                            if(age-n0%2==0){
                                Bullet.shoot0(null);
                            }
                            Bullet.velocity0(norm(velocity,.0001f));
                            Bullet.type0(Bullet.Type.DarkPellet);
                            Bullet.color0(thisCol);
                            this.position1(position);
                            Bullet.position0(position);
                            new Bullet(){
                                public void move(){
                                    super.move();
                                    if(age+n0==drawPeriod){
                                        SE.KIRA.play();
                                        this.type1(Bullet.Type.Pellet);
                                        this.acceleration1(Calc.rotate(norm(this.velocity(),speedMult*randomFloat(.0004f,.004f)),.4f*randomFloat(-1,1)));
                                    }
                                }
                            }.add();
                            i++;
                            
                            if (i >= doubleSets.length) {
                                this.status=Status.Fade;
                            }}
                        }
                        public void draw(Graphics2D g){}
                    };
                    Blah.position1(toF(doubleSets[0]));
                    Blah.add();
                }/*
                 *
                 * Bullet.color0(Col.BROWN); float[] floatpts = toV(-.07f,.5f);
                 * float[] position = sum(product(rotate(floatpts,angle
                 * ),radius),toV(x,y)); Bullet.position0(position); new
                 * Bullet().add(); floatpts = toV(-.1f,.6f); position =
                 * sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                 * Bullet.position0(position); new Bullet().add();
                 *
                 *
                 * floatpts = toV(-.13f,.7f); position =
                 * sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                 * Bullet.position0(position); new Bullet().add();
                 */
            }
        };
        this.declare(60, 12000);
        this.addLoopStart();

        //this.moveBossTowardsPlayer(120, 120, 60);

        this.actionArray.add(drawFace);

        this.moveBossTowardsPlayer(60, breakTime, moveDistance, 60);
        this.addPause(breakTime);
        this.stopBoss();


        this.addLoopEnd();
    }
    
}
