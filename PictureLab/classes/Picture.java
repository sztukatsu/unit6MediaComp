import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

public class Picture extends SimplePicture 
{
  public Picture (){super();}
  public Picture(String fileName){super(fileName);}
  public Picture(int height, int width){super(width,height);}
  public Picture(Picture copyPicture){super(copyPicture);}
  public Picture(BufferedImage image){super(image);}
  public String toString(){String output="Picture, filename "+getFileName()+" height "+getHeight()+" width "+getWidth();return output;}
  
  public void noise()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[]rowArray:pixels)
      {
          for(Pixel obj:rowArray)
          {
            Random rand = new Random();
            int o1 = rand.nextInt(4)+1;int o2 = rand.nextInt(4)+10;int o3 = rand.nextInt(4)+1;int o4 = rand.nextInt(4)+1;
            int r = obj.getRed();int g = obj.getGreen();int b = obj.getBlue();
            r = r+(225-r)*o1/o2;g = g+(225-g)*o3/o2;b= b+(225-b)*o4/o2;
            if(r>255){r=255;}if(g>255){g=255;}if(b>255){b=255;}
            obj.setColor(new Color(r,g,b));
          }
    }
  }
  
  public void greyscale()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[]rowArray:pixels)
      {
          for(Pixel obj:rowArray)
          {
              int r = obj.getRed();int g = obj.getGreen();int b = obj.getBlue();
              int average = (r+g+b)/3;
              Color grey = new Color(average,average,average);
              obj.setColor(grey);
            }
        }
  }

  public void sepia2()
  {
      this.sepia1();
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[]rowArray:pixels)
      {
          for(Pixel obj:rowArray)
          {
              int r = obj.getRed();int g = obj.getGreen();int b = obj.getBlue();
              Color sep;
              if(r<60){sep = new Color(r*9/10,g*9/10,b*9/10,30);}
              else if(r<190){sep = new Color(r*8/10,g*8/10,b,30);}
              else{sep = new Color(r*8/10,g*8/10,b,30);}
              obj.setColor(sep);
            }
        }
  }
  
  public void overlay()
  {
     Pixel[][] pixels = this.getPixels2D();
     Picture filter = new Picture("overlay2.jpg");
     Pixel[][] pixels2 = filter.getPixels2D();
      for(int i=28;i<928;i++)
      {
          for(int j=16;j<429;j++)
          {
              int r = pixels[i][j].getRed();int g = pixels[i][j].getGreen();int b = pixels[i][j].getBlue();
              int r2 = pixels2[i-28][j-16].getRed();int g2 = pixels2[i-28][j-16].getGreen();int b2 = pixels2[i-28][j-16].getBlue();
              Color setme = new Color(r,g,b);Color setme2 = new Color(r2,g2,b2);
              Color setme3 = blend(setme,setme2);
              pixels[i][j].setColor(setme3);
            }
        }
    }
  
  public void overlay2()
  {
     Pixel[][] pixels = this.getPixels2D();
     Picture filter = new Picture("overlay.jpg");
     Pixel[][] pixels2 = filter.getPixels2D();
      for(int i=56;i<956;i++)
      {
          for(int j=445;j<858;j++)
          {
              int r = pixels[i][j].getRed();int g = pixels[i][j].getGreen();int b = pixels[i][j].getBlue();
              int r2 = pixels2[i-56][j-445].getRed();int g2 = pixels2[i-56][j-445].getGreen();int b2 = pixels2[i-56][j-445].getBlue();
              Color setme = new Color(r,g,b);Color setme2 = new Color(r2,g2,b2);
              Color setme3 = blend(setme,setme2);
              pixels[i][j].setColor(setme3);
            }
        }
    }
    
  public void overlay3()
  {
     Pixel[][] pixels = this.getPixels2D();
     Picture filter = new Picture("overlay4.jpg");
     Pixel[][] pixels2 = filter.getPixels2D();
      for(int i=14;i<914;i++)
      {
          for(int j=1303;j<1716;j++)
          {
              int r = pixels[i][j].getRed();int g = pixels[i][j].getGreen();int b = pixels[i][j].getBlue();
              int r2 = pixels2[i-14][j-1303].getRed();int g2 = pixels2[i-14][j-1303].getGreen();int b2 = pixels2[i-14][j-1303].getBlue();
              Color setme = new Color(r,g,b);Color setme2 = new Color(r2,g2,b2);
              Color setme3 = blend(setme,setme2);
              pixels[i][j].setColor(setme3);
            }
        }
    }
    
    public void sepia1()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[]rowArray:pixels)
      {
          for(Pixel obj:rowArray)
          {
              int r = obj.getRed();int g = obj.getGreen();int b = obj.getBlue();
              int or = (int)((r * .393) + (g *.769) + (b * .189));
              if(or>255){or=255;}
              int og = (int)((r * .349) + (g *.686) + (b * .168));
              if(og>255){og=255;}
              int ob = (int)((r * .272) + (g *.534) + (b * .131));
              if(ob>255){ob=255;}
              obj.setColor(new Color(or,og,ob));
            }
        }
  }
  
  public static Color blend(Color c0, Color c1) 
  {
      double totalAlpha = c0.getAlpha() + c1.getAlpha();
      double weight0 = c0.getAlpha() / totalAlpha;
      double weight1 = c1.getAlpha() / totalAlpha;
      double r = weight0 * c0.getRed() + weight1 * c1.getRed();
      double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
      double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
      double a = Math.max(c0.getAlpha(), c1.getAlpha());
      return new Color((int)r,(int)g,(int)b,(int)a);
  }

  public void copy(Picture fromPic, int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  public void copy2(Picture fromPic, int startRow, int startCol, int sr, int sc, int er, int ec)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = sr, toRow = startRow; 
         fromRow < er &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = sc, toCol = startCol; 
           fromCol < ec &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  public void bts()
  {
    Picture shield1 = new Picture("OgZwE6L.png");
    Picture shield2 = new Picture("OgZwE6L.png");
    Picture shield3 = new Picture("OgZwE6L.png");
    Picture shield4 = new Picture("OgZwE6L.png");
    shield1.overlay();
    shield1.sepia2();
    shield2.overlay2();
    shield3.noise();
    shield3.greyscale();
    shield4.overlay3();
    this.copy2(shield1,28,16,28,16,928,429);
    this.copy2(shield2,56,445,56,445,956,858);
    this.copy2(shield3,42,874,42,874,942,1287);
    this.copy2(shield4,14,1303,14,1303,914,1716);
    this.write("trials.png");
  }
  
    public void bts2()
  {
    Picture shield1 = new Picture("OgZwE6L.png");
    Picture shield2 = new Picture("OgZwE6L.png");
    Picture shield3 = new Picture("OgZwE6L.png");
    Picture shield4 = new Picture("OgZwE6L.png");
    this.copy2(shield1,28,16,28,16,928,429);
    this.copy2(shield2,56,445,56,445,956,858);
    this.copy2(shield3,42,874,42,874,942,1287);
    this.copy2(shield4,14,1303,14,1303,914,1716);
    this.write("trial2.png");
  }
} 
