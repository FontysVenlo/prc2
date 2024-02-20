/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package sealedshapes;

/**
 * Simple Circle
 * 
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 * 
 * @param center of the circle
 * @param radius of the circle
 */
public record Circle(BasicPoint center, double radius) implements BasicShape {

}
