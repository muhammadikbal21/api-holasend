package com.enigmacamp.api.holasend.services.mail;

public class EmailContent {

    public static String resetPassword(String to, String username, String secretActivationCode) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                "<meta content=\"width=device-width\" name=\"viewport\"/>\n" +
                "<!--[if !mso]><!-->\n" +
                "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n" +
                "<!--<![endif]-->\n" +
                "<title></title>\n" +
                "<!--[if !mso]><!-->\n" +
                "<!--<![endif]-->\n" +
                "<style type=\"text/css\">\n" +
                "\t\tbody {\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ttable,\n" +
                "\t\ttd,\n" +
                "\t\ttr {\n" +
                "\t\t\tvertical-align: top;\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t* {\n" +
                "\t\t\tline-height: inherit;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ta[x-apple-data-detectors=true] {\n" +
                "\t\t\tcolor: inherit !important;\n" +
                "\t\t\ttext-decoration: none !important;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "<style id=\"media-query\" type=\"text/css\">\n" +
                "\t\t@media (max-width: 520px) {\n" +
                "\n" +
                "\t\t\t.block-grid,\n" +
                "\t\t\t.col {\n" +
                "\t\t\t\tmin-width: 320px !important;\n" +
                "\t\t\t\tmax-width: 100% !important;\n" +
                "\t\t\t\tdisplay: block !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.block-grid {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.col {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.col_cont {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\timg.fullwidth,\n" +
                "\t\t\timg.fullwidthOnMobile {\n" +
                "\t\t\t\tmax-width: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col {\n" +
                "\t\t\t\tmin-width: 0 !important;\n" +
                "\t\t\t\tdisplay: table-cell !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack.two-up .col {\n" +
                "\t\t\t\twidth: 50% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num2 {\n" +
                "\t\t\t\twidth: 16.6% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num3 {\n" +
                "\t\t\t\twidth: 25% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num4 {\n" +
                "\t\t\t\twidth: 33% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num5 {\n" +
                "\t\t\t\twidth: 41.6% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num6 {\n" +
                "\t\t\t\twidth: 50% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num7 {\n" +
                "\t\t\t\twidth: 58.3% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num8 {\n" +
                "\t\t\t\twidth: 66.6% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num9 {\n" +
                "\t\t\t\twidth: 75% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.no-stack .col.num10 {\n" +
                "\t\t\t\twidth: 83.3% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.video-block {\n" +
                "\t\t\t\tmax-width: none !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.mobile_hide {\n" +
                "\t\t\t\tmin-height: 0px;\n" +
                "\t\t\t\tmax-height: 0px;\n" +
                "\t\t\t\tmax-width: 0px;\n" +
                "\t\t\t\tdisplay: none;\n" +
                "\t\t\t\toverflow: hidden;\n" +
                "\t\t\t\tfont-size: 0px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.desktop_hide {\n" +
                "\t\t\t\tdisplay: block !important;\n" +
                "\t\t\t\tmax-height: none !important;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "<style id=\"icon-media-query\" type=\"text/css\">\n" +
                "\t\t@media (max-width: 520px) {\n" +
                "\t\t\t.icons-inner {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner td {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;\">\n" +
                "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                "<table bgcolor=\"#FFFFFF\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#FFFFFF\"><![endif]-->\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 500px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color:transparent;width:500px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num12\" style=\"min-width: 320px; max-width: 500px; display: table-cell; vertical-align: top; width: 500px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]--><img align=\"center\" border=\"0\" class=\"center fixedwidth\" src=\"https://cdn.discordapp.com/attachments/819871715017883648/819871737919307806/holasend_logo.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 100%; max-width: 225px; display: block;\" width=\"225\"/>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid mixed-two-up\" style=\"min-width: 320px; max-width: 500px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"166\" style=\"background-color:transparent;width:166px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 164px; width: 166px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" valign=\"top\" width=\"100%\">\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td align=\"center\" style=\"word-break: break-word; vertical-align: top; padding-bottom: 0px; padding-left: 0px; padding-right: 0px; padding-top: 0px; text-align: center; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                "<h1 style=\"color:#555555;direction:ltr;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:18px;font-weight:normal;letter-spacing:normal;line-height:120%;text-align:center;margin-top:0;margin-bottom:0;\"><strong>Password Reset</strong></h1>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"333\" style=\"background-color:transparent;width:333px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num8\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 328px; width: 333px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<div></div>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid mixed-two-up\" style=\"min-width: 320px; max-width: 500px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"333\" style=\"background-color:transparent;width:333px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num8\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 328px; width: 333px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n" +
                "<div style=\"color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div class=\"txtTinyMce-wrapper\" style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;\">\n" +
                "<p style=\"font-size: 14px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\">Hello, " + username + ". Click this link to reset your password</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"166\" style=\"background-color:transparent;width:166px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 164px; width: 166px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<div align=\"center\" class=\"button-container\" style=\"padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://localhost:3000/recover-password/" + secretActivationCode + "\" style=\"height:31.5pt;width:125.25pt;v-text-anchor:middle;\" arcsize=\"10%\" stroke=\"false\" fillcolor=\"#3AAEE0\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:12px\"><![endif]--><a href=\"http://localhost:3000/recover-password/" + secretActivationCode + "\" style=\"-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #3AAEE0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width: auto; width: auto; border-top: 1px solid #3AAEE0; border-right: 1px solid #3AAEE0; border-bottom: 1px solid #3AAEE0; border-left: 1px solid #3AAEE0; padding-top: 5px; padding-bottom: 5px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:12px;display:inline-block;letter-spacing:undefined;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><span data-mce-style=\"font-size: 12px; line-height: 24px;\" style=\"font-size: 12px; line-height: 24px;\">Reset Password</span></span></span></a>\n" +
                "<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 500px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"background-color:transparent;width:500px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num12\" style=\"min-width: 320px; max-width: 500px; display: table-cell; vertical-align: top; width: 500px;\">\n" +
                "<div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" valign=\"top\" width=\"100%\">\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td align=\"center\" style=\"word-break: break-word; vertical-align: top; padding-top: 5px; padding-right: 0px; padding-bottom: 5px; padding-left: 0px; text-align: center;\" valign=\"top\">\n" +
                "<!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                "<!--[if !vml]><!-->\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\" valign=\"top\">\n" +
                "<!--<![endif]-->\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<!--[if (IE)]></div><![endif]-->\n" +
                "</body>\n" +
                "</html>";
    }
}
