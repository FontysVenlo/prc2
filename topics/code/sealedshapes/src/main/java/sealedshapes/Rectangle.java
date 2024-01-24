/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package sealedshapes;

/**
 * Simple rectangle 
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 * 
 * @param topLeft of the rectangle
 * @param bottomRight of the rectangle
 */
public record Rectangle(BasicPoint topLeft, BasicPoint bottomRight) implements BasicShape {

}
