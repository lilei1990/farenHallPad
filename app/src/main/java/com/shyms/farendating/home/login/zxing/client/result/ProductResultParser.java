/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shyms.farendating.home.login.zxing.client.result;


import com.shyms.farendating.home.login.zxing.BarcodeFormat;
import com.shyms.farendating.home.login.zxing.Result;
import com.shyms.farendating.home.login.zxing.oned.UPCEReader;

/**
 * Parses strings of digits that represent a UPC code.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ProductResultParser extends ResultParser {

  // Treat all UPC and EAN variants as UPCs, in the sense that they are all product barcodes.
  @Override
  public ProductParsedResult parse(Result result) {
    BarcodeFormat format = result.getBarcodeFormat();
    if (!(format == BarcodeFormat.UPC_A || format == BarcodeFormat.UPC_E ||
          format == BarcodeFormat.EAN_8 || format == BarcodeFormat.EAN_13)) {
      return null;
    }
    String rawText = getMassagedText(result);
    int length = rawText.length();
    for (int x = 0; x < length; x++) {
      char c = rawText.charAt(x);
      if (c < '0' || c > '9') {
        return null;
      }
    }
    // Not actually checking the checksum again here    

    String normalizedProductID;
    // Expand UPC-E for purposes of searching
    if (format == BarcodeFormat.UPC_E) {
      normalizedProductID = UPCEReader.convertUPCEtoUPCA(rawText);
    } else {
      normalizedProductID = rawText;
    }

    return new ProductParsedResult(rawText, normalizedProductID);
  }

}