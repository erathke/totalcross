/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2011 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *********************************************************************************/

// $Id: ImageTest.java,v 1.10 2011-01-04 13:19:29 guich Exp $

package tc.test.totalcross.ui.image;

import totalcross.io.IOException;
import totalcross.sys.Settings;
import totalcross.ui.image.ImageException;
import totalcross.unit.ImageComparisionTest;
import totalcross.unit.ImageTester;

/** This one basically tests image alignment and image draw on various bit depths.
 */

public class ImageTest extends ImageComparisionTest
{
   private String _30x30x8_65536 = "58C3858FD94BA26114878F698BB6300C4E49056D4417DA029AA5952D344D84150A6921418B2D97316625485386D5450BED45A5465AD95E567EA445D145D4945D34349F1F5806CDDFD1BC6F31305733CFDD39BFE7FDF11E003F8D161040A7D3198CC0C0A0207F7070480893C962B14243C3C200FC41C16F1BB460D0E94F7E1A9AE8CC18068BC164E23C3C3C222222212131313189F91192696C363B851EC9888A4AE1709E9F01A2A363505B2C2BF4435C785CB8CF875D4192809D9C9CCC656703F078CC98B4B4B4D8D8988CB8385F7C3C3F214180CAB81C2E97CBE301A4883E8923A3A2B81C0EF791C74B4D456EBA2003BBC5F17C3E804020C8140885595959D9D92291589C9353909B5B59299114E7E71714E09CCFE7E7E672F324927CBC118B4442611E9A44EF797A617151515595A2D8FA995F5292F945525AAACA2A2B13D54AC5D2F272804A8C442293C90AD48585551845AB5229AFAEAEA951A9002A2A2AEBEB1B1A1A1BE572B95ADEDCDCD252A5502865D5988A5200954A5556565B2B954ACB6BEBEAEADF5DB5BAA949865D80D656855289CB94DF103D3D3DBDBD06435F9F4663D418FBFB01AC569B9222088A1AFFAAD168DADBB55AADC1D0D6A6D1BC4D005A6D47474767A7AEAB4BA71B1A1A1E1ED1EB47F5DDDD63A86BBCB717A00DB5198C98FEFE7757AFD3198D3A13720747000C8889E9699CCF0D0C0C621616748B4326F3B0596F01181D9D456D63E3E3D68989C9C9A9A9D9E9E99999D9D9B9B9C1C1CDF9798045C48CC964369B2D96A5A5B16504FAB28D5A59995A9DB1032C2CEC2C2E9A4D38DEB758D6D696ED76AB9542F10A45ADAD01D8EDF6F5F58D13EFE6E6FCDEF6F6CECEF9EEEEDEDEFEBEC3B17170707888EFB73A9D1445AC7ADD2EBBCBED5EDFD8F0EE935BBBE7E7AE33F739C0118E0984F3F8D8E5F2789C7727244192F7DB67F7F72F2F001717170F0F8ED74BD47679747475754D10DF899B1BD7EDADC7737707F0704A221BF3E305AB24C679E774DAD0195600EFF529E145BEEBD68D7292C4C6E9A9174151361B80CFF7E87B7C7AF2FB9F1E27961C5399DE8B975F57AF7F40EFDF558477F3D5416EFDC4057FE5AFFFE4FFF96FA53FCB72";
   private String _30x30x4_65536 = "58C37593C18DC5200C44B9D1410AE0EA6290B850118256DC0112F2CDB550C78E4DF237BBDA1D7260FC068758FFC768BA6EC5F8B6B185F0AB72EFDDA7180F4F47A064983E59024FC8DD6914E80E5F041927BA2C82D55A64A39E00338570D5ABE034452F244A058BA845B27008162E94C99E72847DA64AA596025E524DDEAB021434B3241927E38873862AD7548AB5C2E19CED70319EBD40151E4F3ED9C765F0BB70F21F57CB2CF3E137AD87E69703F7B0F56BD45A6F3DF59446EA0DEA3D04AE52654E113E150838B587F73E4CEDA16F97FA994FFA939A0B61DC2FEB3FB8BFE7F001CADC993F1733D7650C35DE18CBC38AC5472292669ACCB87F5B0D79E99E775A190C9F7438F3425C5486F0E2B5164E0F5138CE0B5CAC1B263097D30E33B6F6E50AA1DA804067758C61894E55374B42B0D65B352F74CB3E4A6FE512F02D8AF4E3556563D9CCAD31EE27B8A73CD8B28AF67E237CA3D8EF97E8FC45A8ABE2BB6455D98FEEF362D79AD3CF6EDD6FFEEC74FFA56FBEFFE15FAD199DBF";
   private String _30x30x2_65536 = "58C3A593511284300843B9FF41738E9D6D093CD03FABA34220945023CED2FF92A2AC73F71AA632A01D42FC4A56DA0296A82A3EC984EC7B3BDDE85DD892ED6CC0752AF0F8594626BE8083B2854AD122EE67913771C083180A2B16F2DEBA2346C7D0876D613331F482D4D63EDF25C3EB20963EC5DDA129020737F5E9E3E09E2CC503ED7ADAFA340AEE522C840950E8A0EBA12E474BA585132BBAC7F7D26FA09CD9F80B26C9E27FCBFF8AFF00E751076B";
   private String _31x31x8_65536 = "58C3858FD933EA7118C6BF8A13D198311D4B5933719371D3241A15C932A1A46618AEB8C00D571A271D29B49CE1C6D046A6EC5A642D4BFDAC7F80ABA44932F83B3ADF5F9673AECE79EEDEE7F3CC67E605202B2909832162B1C9C92929A9DFBEE170B8D4D4B4343C1E9F9E9E910162B1A74441C6E2F1582C2616C3E130EF179E4C869C402014176766665228A5A5202B8B4AC51089E5DF93B3737268B9B9CFCF80442291D328F8FC820202212F2F0A2F3299022F6A19954A2596419E46AEC8CF874D616151D16371A4A48442A197D25115954804D5E535D99F32129B5601B70595B4C2425ED163046E011C575632995555554C6675754D4D0D8BC5A98561B3D91C0E970BE868188C4FCEE5B2586C4615CA39095E57C79648783C5E43039FDFD8D8D8D4D4CC68EE6961320502416B6B5B1B1071A4B5F55269035BC4E542FEC617B60BA52291A89F23168B0502D0DEDE2E14C2BBA3432CEEEC1449240303D2C1B7A1A1AEAEAEA6EEE666D0D3D3D2DBFB2113A241B7FDE876680006F4F5F541192CC41C5A7D7D118F1789F0298D743A9FBF86060C7EC8BABB272694CAC9E1E19111955A3D35353D3D3D33A3D100AD765426D3EBB53F60E472F9F8B842A1F8A9552A135BD50818D5EA64329D4E333686F2F10457CC4E189446CDE4A45905D45F329346ABD5EA747ABD7CEC97D53A3B2B9F9B53D8C092DE3C3FAF34184D2695CA625958549B4C462BBCCC66B3C5B2B4046C3693C1608485092D1616160CDB46A303BD5C1618B0BC6CB55A57569C73FB369BDD6E5A5D854FAD6F6C6C6C6E6E6D6D6D6F839D1DA3C7E3749A0F2CEEA5BD3DCFCE8EC3E170EEBB5C87EEF0FA3AFCFFBD70BA5C2EB77B7779D7E3D9DB73EE1F1C1C1CDA1D47ABC76B60DDEB0DFB364F4E4E4FCFCECECFCFFDFE402080842E2E2E2FAFAEAEAEAF012CC2010439F1F920F7C3DCDD21081208A0F8EAF515DCDCDCDEDEC6E3FEA3A3E363AFD7EBF39D4097FFCB0590BF64E836787F1F0C210F0FE1307C821F01619FEFC1B77A06BFF2FBA12914BA0D427F2884243661F0F8188D469F9E62B1D873D46E5F7C79797DF5BF21F1CF003844A73008DA5E5C0483C1D0FD1F1EFF77FECB7F03126CE544";
   private String _31x31x4_65536 = "58C38593C1CDE4200C85DD07A2028A41E2E272382297E21A90C5853EB8A58EFFD94936ABFFB0FB88347C7ECF2699CC9C134A50CEF901C70C3E9DBE022AE9AFB0633F74FAE9E9EB2EE5E9CEB97894BE61DDEDB73723E817FDB171988F2A7773F651485369ED1D560E97C4D1DC4FEDD8A7448E0DD1524B6908BBB007310329B7D4B26379FC48D7C28513C36F8DB522CF50B8D8D7E88E51C45C8B56451C37B21A10628F372CA6FA31C4551D1F6AB586FFF2EF6C759FA3E25CAA9EEAB7957114B34E5525BE9B717568F401C910E981831CA131025F7AB2C97DF92A6FF3E8DA752404483256EFBFBAF525F2D108EB6BA6C031EC46F8AA1E5754C4F202EA988E4B4C168134E226B2057BD7625B79AD85E70B361B5B909F7864735AB2E1B2FB73180413858EEF04AEEDBD454CF15EC8261BCBE2B5A64E28C2689E21A3BBB07062F0F4BD2DB3DB36321F765D73562CC6301F75BBE1FBAC77D893DD31C03CCAE41F16AFCA26DCBDF6F6239E538C4AC2AFFCFEDB14113C358E660C71ADEBA2ED778B3BF4E5A575EDEBB15D74FD5BFFF57F002580FA05";
   private String _31x31x2_65536 = "58C38593C1CDE4200C85DD07A2028A41E2E272382297E21A90C5853EB8A58EFFD94936ABFFB0FB88347C7ECF2699CC9C134A50CEF901C70C3E9DBE022AE9AFB0633F74FAE9E9EB2EE5E9CEB97894BE61DDEDB73723E817FDB171988F2A7773F651485369ED1D560E97C4D1DC4FEDD8A7448E0DD1524B6908BBB007310329B7D4B26379FC48D7C28513C36F8DB522CF50B8D8D7E88E51C45C8B56451C37B21A10628F372CA6FA31C4551D1F6AB586FFF2EF6C759FA3E25CAA9EEAB7957114B34E5525BE9B717568F401C910E981831CA131025F7AB2C97DF92A6FF3E8DA752404483256EFBFBAF525F2D108EB6BA6C031EC46F8AA1E5754C4F202EA988E4B4C168134E226B2057BD7625B79AD85E70B361B5B909F7864735AB2E1B2FB73180413858EEF04AEEDBD454CF15EC8261BCBE2B5A64E28C2689E21A3BBB07062F0F4BD2DB3DB36321F765D73562CC6301F75BBE1FBAC77D893DD31C03CCAE41F16AFCA26DCBDF6F6239E538C4AC2AFFCFEDB14113C358E660C71ADEBA2ED778B3BF4E5A575EDEBB15D74FD5BFFF57F002580FA05";
   private String _32x32x8_65536 = "58C3A58FEB4B9A611C86DBB0A67664AC5A66C38A6C34888A956687B7361D9E2AB55A445FDCB7A8694D4D361B6DD824C559B082EC80609ED25A3A176A69BA70600DF253E1FB1A9A1FF2EFD89E8776F803767DBB9FEBE6FEF1E4E4646EDDBA7D1B5F5A8AC3E5E6E6E6E5E5DDB983C713080422313F3FBFA0209377F5F70187C36532309240CC271209848282C2C2220AA5B8B8B8A4A4B6167F37A7AEEE1E955A5A5A56862B2F4FDFBF5F51515404CB8492AAAA624AE1A386A2348942682490C9642AB50E94EF9148F8CA4A22995C55957AD0D4DC4CA150AAAB6B6A6A6B6BDAEE024FA7D6D73F649431CA1F36343CAA489348954823EC3637353535A75A5AAAAB1B5960AB9E4EEFECA475D111A4A787C16020DD5D5D5D4C26134158AC9AC78F5B5B696D6D341A9DDEDEDE0E6C47476727F0DDDD08ACF7F6B29E42984C168BF5EC596F2BC2A171385C2E97D7CEE333FA3A7A7A90274FA066420D40D86CDA0097CEE5217C487FBF7860402010088522D1E0E0E0D0D0D0F0F0F8F8C8C8C8C4C4E8E4289B2D6673C6C6C09A50C8EF130A85E2172F9E3F17084422D1E8E848362B168F71A0E5F1F8FCBE3E413FD0BFB7C0D8F8CD56166E4D4C4E7E7A299148A452E9D4D4F4F4AB57D332994C2E5728AC9797369BDD61B7D93432B946AE90CECCCC2895EA29A94CA6D168A45298944A9DF2F5EB376F542AD5ECECECDBB74B4B9239C93BC927E9FBA965351C53C8E57AB9DCA830C0B24EA752E9F57AA3EAA3C1A05C54ABD5F3F39A0FB255A0D71666B45AAD4EA7DF04DE6432180C8B8B4B4B6B7373967796F7D665DB8ADA3E3FBFBCFD6175D568342E2CAC6BB51B1B26937E75CF6874ADADADAF8368B55A005697CDB69274381C2ED7E6E6A65BEF31815BDE45B3D9BCB505B515FEC90EFCF6B6D3E974B9767676306CE3B3676FCFED767B3C5FBE78BDDEAF5F435BFBB198C519023E99DCDDC53017F41E8FC70BBDD7EB3CDC0F067D3E9FDF1F08D80F0E4E0ECF82C150E8E8E8281C8E44C2DFBE1D1FBBCF3D2062188A867DBE582C7612F79D02D01FA7A1F8D9D9F1F9F9F5F54534FA1D10DD07B74E7C400702818383F859301E3F09F94EC1161A891CC331D0BEB800D59F89048A61C964F212D0E2F7FB6D7ECBCDB776BE7BC1E168145C8084410B4BA552E9D4D5D5554EA622BD62363B5AB1EBEB6C36F1F30F6802BD218CA23047C07CE29FFE6F7E0165A00212";
   private String _32x32x4_65536 = "58C3AD93C1CD2B210C840BF19123C5208B9A1035F84C1396906FF4C16DEBF8C766C34BEE6F58ADF876C643A26CF676D126A204ED83F4839F0758DF69DC76FBA0C7E1E68CEB1D3FA3FDBAE4EEADCA948144F7B0DDF2D884CBDD94F211E554A9A608FB301F73200B8FA8B2E733C32F99B9BA1CF2E0C1CC82704DC5CDCCC886EFE4618A38A3AB640F235013864B295CEA5B167EF63252D2AA5430CC995FBB84B8C470ADE36094215C5E05A391B19FB823A92F5E17FE6F17CF6B7AD968BBB5468D7AF2D5427D188BA92ADBE1D65DA9B76F84822E36EF1A7D7742537AD372C3CE72ABDEB3048F5CD27FB00B9ADA90212249531200F6A38BE082DA98C70D0D970D93F8D462989D0DE9DE57FFD8986751D1A403B313612C9325001060C95ACB866394E199AA9DD189F44240E04236CD4F529D4766B66CE9F2FD08545F933FAEF152FB17463CCE9A651663E3AF2EAFBE5DB7FA791E8D4A1666FC3C283C5F2A9A7479A5BE1B51BCC43BBFFF327C7F4D6678419EABF5AC385FEDD1C3FF577FA2E71B2D";
   private String _32x32x2_65536 = "58C3B59341168340084373FF83E61C7D6A033FEAEBAE6C9C0C040838D265960F5B24C00B1F1FBA839DF07276AEF386CE50A7142F9C6A5E84265D3DA7D83732047F7D484D582847402D5D11EFC8CA71C245B649EDE06A2EA907EE243B1745CF347B536A911A8D295E8BDA5983BA954F80B6BDDC5DFC7D6F64431436FE26B1FF099039FA1A88FC80AA4D61B3ACEC26FBB6E6518BFC3DFD47B256F5B0790633DF37DB4725FD48F637FB00677F4E65";
   private String _33x33x8_65536 = "58C38DD0ED4B9A6B1C07F066CEC8ED9090357C88EC5414959239CBDC349BBA22AA491119522CABD168E58B607596D89DEBC11611B28E6435333525E7A6E9B027652B851114048314DB8D0AFD1F9DDF6D87CE79B77DDF5C17179FEFF7C5954824EEDCC1E170E9E9E978FCDDBB38028142A150A999B44C22F1DE3DE2FDFB696969094222232365E8443C1E8F8B13323228545C66269E4824D26929939FFF07839195954522910A4989A2447636994CCEC9C9CDCD7DF08042209453C024930C1A834623D1E92406A33C4E495268541A9D4ECF2B25938B8B8B934930D40A2A8D4967B1F2F260373F1F65A00505050FFF2C2C641615656767F3C860C83925A5D86E39968AAA0A26930505161B0B8A5649696098CC5216ABB4A4A4ACAC2C29ACE0314B3053C76457B25171D54F1A8703E621EC7285D5D535353C1EAFA496CFE73F7AC47FFC5820100A85757575221198FA069144222EBF79128958F50D605385DA9B0266EA454F9E7488C51289442A7DFAB48BDB50DD58ADA8696AE275D7F6F09B5B5AC00804BD02814C26ACEB8395F6FACE0E88582693BE94CBE55D437285024C6B6BEBB35E9952D9D7D7DFD6D636207BF102D0E0E04BCCC8BBE48D8D60140A455353777777734F734BCFF3E7BDBDCF94A942FF407B7B075600A354CA3A3BFBFBE5035D72144DFD0987837D22F73D82E8F57A040133F8EFEED090F4D5ABE1E1919111954AF55AFBF6AD6A7A7A7474747616CCDCDC9C4E373636363E3EFED7C29B371313136AB57A49ADD168268791114435851995EEB50EF2EEDDFF8C7AC2A0D123930882CC6146AB855DC4303D3333B3326B844DDDFC3C14165285C545B31ACCBC4E8B70260D06E820C6A929EDF2B2D1B8B2BEFEE183C964DAD8800A98A5A5398B46AF9FB421B6A9F776EDF2DF5C83C1009B2B88D1B8BABABAB606667D7D7D61C163322D2E6E98CD9B9B4E8BC56AB5DA6C5B9731BBDDC1B51F1CDC18776A77C76C366F6C982C966DABD3668BC55C767B2C163BB080D9DE763A9DC18F1F5D2ED727D7E7CF6EB7DBE3F1ECEC78BD5E9FCFFAE5CB776BCAD8B6B6A0E070C49C01B73B10080483C170D8E70B7B3CEEC3ED40008CDF6FDBDDDDDBB3EFEF9F1C9C6E1FDE9868F4EBD7D0B76F474747C7C76082A160F8C2771DF90E39F3FBFD58616FFFFC100B14424130A1D045247A110A9D9E9E9D05FCFEE049F0FCFC7CFF3C7006002AFE2D30516CF707B6FBE3F80A0AE170E43A128DC6B05C5E5E727E8281532A65575656B2D9369BD7FBC9E180D19393482412050A0183A268128DC7E389443C59B5E95BE372AFAEAE0677AF6F0306966F73FBFEDF2D65AE7F95DF34FF0061D6239E";
   private String _33x33x4_65536 = "58C38D93318EEC20104427E62A2DF555081CF4710821EDF32044C23D9C718EAD6AE3FD2BFDD5D72FCF685CCDAB821979EE3B945EE15EEE5B5F5BEECFE773105E0709EAD8872977B9EBE335B1E24769D0600484A45493B6548443C54EB83409740BF75281578DDE2245A5EA2361694E6452ACAB84540EE0E2E18D4C4C2FA6E4B63F109062CE3B324F6D8E975ED7C537EF4D4C4D2E32189AA137C31BFA0060191088E0C9E8E5923D470C33D59C2567CB8C5C0F038F4C0640E232834702EB81E40C26872CA66472A785E685D7FC66ECD45E276027102E187B8B45E257C1B1A851ABBBD702C62205645A816AA841A93C1ECC99B4F24DFCB01C04D37E61AA176C4386BF615302FE127F07C8B454B5E070359A11A9CF61DFCE60D8CB6AAB3A51AA0050E275D4D51E262623421C0DA7860F1B3AA7CEDEC914EFCED4686D21EBB0C36D8E36754CEB0EA6F78A14883557EBDE7B87C13614F84C86B1C94BA70FDC0F32586F6BD40ECFBD86B9A1B34DF4F6D332E69A731C4B06A9BDF6CEBDE78E9322401DE065F69E7B446A587F19AE76EBC88059FB4DC5367BA113A128B46ACA33E313CF331E0A97E10BE7980BA8ED8DEF39E1379F5529F8673EFF726DABE1C1018203BC02C38F75AE5F75987FEA3F992FA2F32A05";
   private String _33x33x2_65536 = "58C3AD53510E433108F2FE07E51C4B5310B44BF6B3EE675A048BBEAA7B707EC08C143A7B2E2A4115A05300671824CBF91F88A5CA14CB547039148710DC52DCC317EAB611BA214F03BA0FD6862448036658635DBE8AA404E6EBA335C5A44C44345A03E29AC572CD0C4A54BFAB564A1D97DF2E47C2B85A3EAFCC0ED3C4D1BEA2D69C3B61D518BF3C40388B1CCCF079EEC45A346B7813C624BEDAB87D46D43DF89AA835287D077B168856736D47D19E7F0B4E128C859477E80FEC391596874F2F2E96C4BAEFA2FD3A7FC47C00B6157089";

   private String _30x30x8_256 = "58C3858FD94BA26114878F698BB6300C4E49056D4417DA029AA5952D344D84150A6921418B2D97316625485386D5450BED45A5465AD95E567EA445D145D4945D34349F1F5806CDDFD1BC6F31305733CFDD39BFE7FDF11E003F8D161040A7D3198CC0C0A0207F7070480893C962B14243C3C200FC41C16F1BB460D0E94F7E1A9AE8CC18068BC164E23C3C3C222222212131313189F91192696C363B851EC9888A4AE1709E9F01A2A363505B2C2BF4435C785CB8CF875D4192809D9C9CCC656703F078CC98B4B4B4D8D8988CB8385F7C3C3F214180CAB81C2E97CBE301A4883E8923A3A2B81C0EF791C74B4D456EBA2003BBC5F17C3E804020C8140885595959D9D92291589C9353909B5B59299114E7E71714E09CCFE7E7E672F324927CBC118B4442611E9A44EF797A617151515595A2D8FA995F5292F945525AAACA2A2B13D54AC5D2F272804A8C442293C90AD48585551845AB5229AFAEAEA951A9002A2A2AEBEB1B1A1A1BE572B95ADEDCDCD252A5502865D5988A5200954A5556565B2B954ACB6BEBEAEADF5DB5BAA949865D80D656855289CB94DF103D3D3DBDBD06435F9F4663D418FBFB01AC569B9222088A1AFFAAD168DADBB55AADC1D0D6A6D1BC4D005A6D47474767A7AEAB4BA71B1A1A1E1ED1EB47F5DDDD63A86BBCB717A00DB5198C98FEFE7757AFD3198D3A13720747000C8889E9699CCF0D0C0C621616748B4326F3B0596F01181D9D456D63E3E3D68989C9C9A9A9D9E9E99999D9D9B9B9C1C1CDF9798045C48CC964369B2D96A5A5B16504FAB28D5A59995A9DB1032C2CEC2C2E9A4D38DEB758D6D696ED76AB9542F10A45ADAD01D8EDF6F5F58D13EFE6E6FCDEF6F6CECEF9EEEEDEDEFEBEC3B17170707888EFB73A9D1445AC7ADD2EBBCBED5EDFD8F0EE935BBBE7E7AE33F739C0118E0984F3F8D8E5F2789C7727244192F7DB67F7F72F2F001717170F0F8ED74BD47679747475754D10DF899B1BD7EDADC7737707F0704A221BF3E305AB24C679E774DAD0195600EFF529E145BEEBD68D7292C4C6E9A9174151361B80CFF7E87B7C7AF2FB9F1E27961C5399DE8B975F57AF7F40EFDF558477F3D5416EFDC4057FE5AFFFE4FFF96FA53FCB72";
   private String _30x30x4_256 = "58C37593C18DC5200C44B9D1410AE0EA6290B850118256DC0112F2CDB550C78E4DF237BBDA1D7260FC068758FFC768BA6EC5F8B6B185F0AB72EFDDA7180F4F47A064983E59024FC8DD6914E80E5F041927BA2C82D55A64A39E00338570D5ABE034452F244A058BA845B27008162E94C99E72847DA64AA596025E524DDEAB021434B3241927E38873862AD7548AB5C2E19CED70319EBD40151E4F3ED9C765F0BB70F21F57CB2CF3E137AD87E69703F7B0F56BD45A6F3DF59446EA0DEA3D04AE52654E113E150838B587F73E4CEDA16F97FA994FFA939A0B61DC2FEB3FB8BFE7F001CADC993F1733D7650C35DE18CBC38AC5472292669ACCB87F5B0D79E99E775A190C9F7438F3425C5486F0E2B5164E0F5138CE0B5CAC1B263097D30E33B6F6E50AA1DA804067758C61894E55374B42B0D65B352F74CB3E4A6FE512F02D8AF4E3556563D9CCAD31EE27B8A73CD8B28AF67E237CA3D8EF97E8FC45A8ABE2BB6455D98FEEF362D79AD3CF6EDD6FFEEC74FFA56FBEFFE15FAD199DBF";
   private String _30x30x2_256 = "58C3A593511284300843B9FF41738E9D6D093CD03FABA34220945023CED2FF92A2AC73F71AA632A01D42FC4A56DA0296A82A3EC984EC7B3BDDE85DD892ED6CC0752AF0F8594626BE8083B2854AD122EE67913771C083180A2B16F2DEBA2346C7D0876D613331F482D4D63EDF25C3EB20963EC5DDA129020737F5E9E3E09E2CC503ED7ADAFA340AEE522C840950E8A0EBA12E474BA585132BBAC7F7D26FA09CD9F80B26C9E27FCBFF8AFF00E751076B";
   private String _31x31x8_256 = "58C3858FD933EA7118C6BF8A13D198311D4B5933719371D3241A15C932A1A46618AEB8C00D571A271D29B49CE1C6D046A6EC5A642D4BFDAC7F80ABA44932F83B3ADF5F9673AECE79EEDEE7F3CC67E605202B2909832162B1C9C92929A9DFBEE170B8D4D4B4343C1E9F9E9E910162B1A74441C6E2F1582C2616C3E130EF179E4C869C402014176766665228A5A5202B8B4AC51089E5DF93B3737268B9B9CFCF80442291D328F8FC820202212F2F0A2F3299022F6A19954A2596419E46AEC8CF874D616151D16371A4A48442A197D25115954804D5E535D99F32129B5601B70595B4C2425ED163046E011C575632995555554C6675754D4D0D8BC5A98561B3D91C0E970BE868188C4FCEE5B2586C4615CA39095E57C79648783C5E43039FDFD8D8D8D4D4CC68EE6961320502416B6B5B1B1071A4B5F55269035BC4E542FEC617B60BA52291A89F23168B0502D0DEDE2E14C2BBA3432CEEEC1449240303D2C1B7A1A1AEAEAEA6EEE666D0D3D3D2DBFB2113A241B7FDE876680006F4F5F541192CC41C5A7D7D118F1789F0298D743A9FBF86060C7EC8BABB272694CAC9E1E19111955A3D35353D3D3D33A3D100AD765426D3EBB53F60E472F9F8B842A1F8A9552A135BD50818D5EA64329D4E333686F2F10457CC4E189446CDE4A45905D45F329346ABD5EA747ABD7CEC97D53A3B2B9F9B53D8C092DE3C3FAF34184D2695CA625958549B4C462BBCCC66B3C5B2B4046C3693C1608485092D1616160CDB46A303BD5C1618B0BC6CB55A57569C73FB369BDD6E5A5D854FAD6F6C6C6C6E6E6D6D6D6F839D1DA3C7E3749A0F2CEEA5BD3DCFCE8EC3E170EEBB5C87EEF0FA3AFCFFBD70BA5C2EB77B7779D7E3D9DB73EE1F1C1C1CDA1D47ABC76B60DDEB0DFB364F4E4E4FCFCECECFCFFDFE402080842E2E2E2FAFAEAEAEAF012CC2010439F1F920F7C3DCDD21081208A0F8EAF515DCDCDCDEDEC6E3FEA3A3E363AFD7EBF39D4097FFCB0590BF64E836787F1F0C210F0FE1307C821F01619FEFC1B77A06BFF2FBA12914BA0D427F2884243661F0F8188D469F9E62B1D873D46E5F7C79797DF5BF21F1CF003844A73008DA5E5C0483C1D0FD1F1EFF77FECB7F03126CE544";
   private String _31x31x4_256 = "58C38593C1CDE4200C85DD07A2028A41E2E272382297E21A90C5853EB8A58EFFD94936ABFFB0FB88347C7ECF2699CC9C134A50CEF901C70C3E9DBE022AE9AFB0633F74FAE9E9EB2EE5E9CEB97894BE61DDEDB73723E817FDB171988F2A7773F651485369ED1D560E97C4D1DC4FEDD8A7448E0DD1524B6908BBB007310329B7D4B26379FC48D7C28513C36F8DB522CF50B8D8D7E88E51C45C8B56451C37B21A10628F372CA6FA31C4551D1F6AB586FFF2EF6C759FA3E25CAA9EEAB7957114B34E5525BE9B717568F401C910E981831CA131025F7AB2C97DF92A6FF3E8DA752404483256EFBFBAF525F2D108EB6BA6C031EC46F8AA1E5754C4F202EA988E4B4C168134E226B2057BD7625B79AD85E70B361B5B909F7864735AB2E1B2FB73180413858EEF04AEEDBD454CF15EC8261BCBE2B5A64E28C2689E21A3BBB07062F0F4BD2DB3DB36321F765D73562CC6301F75BBE1FBAC77D893DD31C03CCAE41F16AFCA26DCBDF6F6239E538C4AC2AFFCFEDB14113C358E660C71ADEBA2ED778B3BF4E5A575EDEBB15D74FD5BFFF57F002580FA05";
   private String _31x31x2_256 = "58C38593C1CDE4200C85DD07A2028A41E2E272382297E21A90C5853EB8A58EFFD94936ABFFB0FB88347C7ECF2699CC9C134A50CEF901C70C3E9DBE022AE9AFB0633F74FAE9E9EB2EE5E9CEB97894BE61DDEDB73723E817FDB171988F2A7773F651485369ED1D560E97C4D1DC4FEDD8A7448E0DD1524B6908BBB007310329B7D4B26379FC48D7C28513C36F8DB522CF50B8D8D7E88E51C45C8B56451C37B21A10628F372CA6FA31C4551D1F6AB586FFF2EF6C759FA3E25CAA9EEAB7957114B34E5525BE9B717568F401C910E981831CA131025F7AB2C97DF92A6FF3E8DA752404483256EFBFBAF525F2D108EB6BA6C031EC46F8AA1E5754C4F202EA988E4B4C168134E226B2057BD7625B79AD85E70B361B5B909F7864735AB2E1B2FB73180413858EEF04AEEDBD454CF15EC8261BCBE2B5A64E28C2689E21A3BBB07062F0F4BD2DB3DB36321F765D73562CC6301F75BBE1FBAC77D893DD31C03CCAE41F16AFCA26DCBDF6F6239E538C4AC2AFFCFEDB14113C358E660C71ADEBA2ED778B3BF4E5A575EDEBB15D74FD5BFFF57F002580FA05";
   private String _32x32x8_256 = "58C3A58FEB4B9A611C86DBB0A67664AC5A66C38A6C34888A956687B7361D9E2AB55A445FDCB7A8694D4D361B6DD824C559B082EC80609ED25A3A176A69BA70600DF253E1FB1A9A1FF2EFD89E8776F803767DBB9FEBE6FEF1E4E4646EDDBA7D1B5F5A8AC3E5E6E6E6E5E5DDB983C713080422313F3FBFA0209377F5F70187C36532309240CC271209848282C2C2220AA5B8B8B8A4A4B6167F37A7AEEE1E955A5A5A56862B2F4FDFBF5F51515404CB8492AAAA624AE1A386A2348942682490C9642AB50E94EF9148F8CA4A22995C55957AD0D4DC4CA150AAAB6B6A6A6B6BDAEE024FA7D6D73F649431CA1F36343CAA489348954823EC3637353535A75A5AAAAB1B5960AB9E4EEFECA475D111A4A787C16020DD5D5D5D4C26134158AC9AC78F5B5B696D6D341A9DDEDEDE0E6C47476727F0DDDD08ACF7F6B29E42984C168BF5EC596F2BC2A171385C2E97D7CEE333FA3A7A7A90274FA066420D40D86CDA0097CEE5217C487FBF7860402010088522D1E0E0E0D0D0D0F0F0F8F8C8C8C8C4C4E8E4289B2D6673C6C6C09A50C8EF130A85E2172F9E3F17084422D1E8E848362B168F71A0E5F1F8FCBE3E413FD0BFB7C0D8F8CD56166E4D4C4E7E7A299148A452E9D4D4F4F4AB57D332994C2E5728AC9797369BDD61B7D93432B946AE90CECCCC2895EA29A94CA6D168A45298944A9DF2F5EB376F542AD5ECECECDBB74B4B9239C93BC927E9FBA965351C53C8E57AB9DCA830C0B24EA752E9F57AA3EAA3C1A05C54ABD5F3F39A0FB255A0D71666B45AAD4EA7DF04DE6432180C8B8B4B4B6B7373967796F7D665DB8ADA3E3FBFBCFD6175D568342E2CAC6BB51B1B26937E75CF6874ADADADAF8368B55A005697CDB69274381C2ED7E6E6A65BEF31815BDE45B3D9BCB505B515FEC90EFCF6B6D3E974B9767676306CE3B3676FCFED767B3C5FBE78BDDEAF5F435BFBB198C519023E99DCDDC53017F41E8FC70BBDD7EB3CDC0F067D3E9FDF1F08D80F0E4E0ECF82C150E8E8E8281C8E44C2DFBE1D1FBBCF3D2062188A867DBE582C7612F79D02D01FA7A1F8D9D9F1F9F9F5F54534FA1D10DD07B74E7C400702818383F859301E3F09F94EC1161A891CC331D0BEB800D59F89048A61C964F212D0E2F7FB6D7ECBCDB776BE7BC1E168145C8084410B4BA552E9D4D5D5554EA622BD62363B5AB1EBEB6C36F1F30F6802BD218CA23047C07CE29FFE6F7E0165A00212";
   private String _32x32x4_256 = "58C3AD93C1CD2B210C840BF19123C5208B9A1035F84C1396906FF4C16DEBF8C766C34BEE6F58ADF876C643A26CF676D126A204ED83F4839F0758DF69DC76FBA0C7E1E68CEB1D3FA3FDBAE4EEADCA948144F7B0DDF2D884CBDD94F211E554A9A608FB301F73200B8FA8B2E733C32F99B9BA1CF2E0C1CC82704DC5CDCCC886EFE4618A38A3AB640F235013864B295CEA5B167EF63252D2AA5430CC995FBB84B8C470ADE36094215C5E05A391B19FB823A92F5E17FE6F17CF6B7AD968BBB5468D7AF2D5427D188BA92ADBE1D65DA9B76F84822E36EF1A7D7742537AD372C3CE72ABDEB3048F5CD27FB00B9ADA90212249531200F6A38BE082DA98C70D0D970D93F8D462989D0DE9DE57FFD8986751D1A403B313612C9325001060C95ACB866394E199AA9DD189F44240E04236CD4F529D4766B66CE9F2FD08545F933FAEF152FB17463CCE9A651663E3AF2EAFBE5DB7FA791E8D4A1666FC3C283C5F2A9A7479A5BE1B51BCC43BBFFF327C7F4D6678419EABF5AC385FEDD1C3FF577FA2E71B2D";
   private String _32x32x2_256 = "58C3B59341168340084373FF83E61C7D6A033FEAEBAE6C9C0C040838D265960F5B24C00B1F1FBA839DF07276AEF386CE50A7142F9C6A5E84265D3DA7D83732047F7D484D582847402D5D11EFC8CA71C245B649EDE06A2EA907EE243B1745CF347B536A911A8D295E8BDA5983BA954F80B6BDDC5DFC7D6F64431436FE26B1FF099039FA1A88FC80AA4D61B3ACEC26FBB6E6518BFC3DFD47B256F5B0790633DF37DB4725FD48F637FB00677F4E65";
   private String _33x33x8_256 = "58C38DD0ED4B9A6B1C07F066CEC8ED9090357C88EC5414959239CBDC349BBA22AA491119522CABD168E58B607596D89DEBC11611B28E6435333525E7A6E9B027652B851114048314DB8D0AFD1F9DDF6D87CE79B77DDF5C17179FEFF7C5954824EEDCC1E170E9E9E978FCDDBB38028142A150A999B44C22F1DE3DE2FDFB696969094222232365E8443C1E8F8B13323228545C66269E4824D26929939FFF07839195954522910A4989A2447636994CCEC9C9CDCD7DF08042209453C024930C1A834623D1E92406A33C4E495268541A9D4ECF2B25938B8B8B934930D40A2A8D4967B1F2F260373F1F65A00505050FFF2C2C641615656767F3C860C83925A5D86E39968AAA0A26930505161B0B8A5649696098CC5216ABB4A4A4ACAC2C29ACE0314B3053C76457B25171D54F1A8703E621EC7285D5D535353C1EAFA496CFE73F7AC47FFC5820100A85757575221198FA069144222EBF79128958F50D605385DA9B0266EA454F9E7488C51289442A7DFAB48BDB50DD58ADA8696AE275D7F6F09B5B5AC00804BD02814C26ACEB8395F6FACE0E88582693BE94CBE55D437285024C6B6BEBB35E9952D9D7D7DFD6D636207BF102D0E0E04BCCC8BBE48D8D60140A455353777777734F734BCFF3E7BDBDCF94A942FF407B7B075600A354CA3A3BFBFBE5035D72144DFD0987837D22F73D82E8F57A040133F8EFEED090F4D5ABE1E1919111954AF55AFBF6AD6A7A7A7474747616CCDCDC9C4E373636363E3EFED7C29B371313136AB57A49ADD168268791114435851995EEB50EF2EEDDFF8C7AC2A0D123930882CC6146AB855DC4303D3333B3326B844DDDFC3C14165285C545B31ACCBC4E8B70260D06E820C6A929EDF2B2D1B8B2BEFEE183C964DAD8800A98A5A5398B46AF9FB421B6A9F776EDF2DF5C83C1009B2B88D1B8BABABAB606667D7D7D61C163322D2E6E98CD9B9B4E8BC56AB5DA6C5B9731BBDDC1B51F1CDC18776A77C76C366F6C982C966DABD3668BC55C767B2C163BB080D9DE763A9DC18F1F5D2ED727D7E7CF6EB7DBE3F1ECEC78BD5E9FCFFAE5CB776BCAD8B6B6A0E070C49C01B73B10080483C170D8E70B7B3CEEC3ED40008CDF6FDBDDDDDBB3EFEF9F1C9C6E1FDE9868F4EBD7D0B76F474747C7C76082A160F8C2771DF90E39F3FBFD58616FFFFC100B14424130A1D045247A110A9D9E9E9D05FCFEE049F0FCFC7CFF3C7006002AFE2D30516CF707B6FBE3F80A0AE170E43A128DC6B05C5E5E727E8281532A65575656B2D9369BD7FBC9E180D19393482412050A0183A268128DC7E389443C59B5E95BE372AFAEAE0677AF6F0306966F73FBFEDF2D65AE7F95DF34FF0061D6239E";
   private String _33x33x4_256 = "58C38D93318EEC20104427E62A2DF555081CF4710821EDF32044C23D9C718EAD6AE3FD2BFDD5D72FCF685CCDAB821979EE3B945EE15EEE5B5F5BEECFE773105E0709EAD8872977B9EBE335B1E24769D0600484A45493B6548443C54EB83409740BF75281578DDE2245A5EA2361694E6452ACAB84540EE0E2E18D4C4C2FA6E4B63F109062CE3B324F6D8E975ED7C537EF4D4C4D2E32189AA137C31BFA0060191088E0C9E8E5923D470C33D59C2567CB8C5C0F038F4C0640E232834702EB81E40C26872CA66472A785E685D7FC66ECD45E276027102E187B8B45E257C1B1A851ABBBD702C62205645A816AA841A93C1ECC99B4F24DFCB01C04D37E61AA176C4386BF615302FE127F07C8B454B5E070359A11A9CF61DFCE60D8CB6AAB3A51AA0050E275D4D51E262623421C0DA7860F1B3AA7CEDEC914EFCED4686D21EBB0C36D8E36754CEB0EA6F78A14883557EBDE7B87C13614F84C86B1C94BA70FDC0F32586F6BD40ECFBD86B9A1B34DF4F6D332E69A731C4B06A9BDF6CEBDE78E9322401DE065F69E7B446A587F19AE76EBC88059FB4DC5367BA113A128B46ACA33E313CF331E0A97E10BE7980BA8ED8DEF39E1379F5529F8673EFF726DABE1C1018203BC02C38F75AE5F75987FEA3F992FA2F32A05";
   private String _33x33x2_256 = "58C3AD53510E433108F2FE07E51C4B5310B44BF6B3EE675A048BBEAA7B707EC08C143A7B2E2A4115A05300671824CBF91F88A5CA14CB547039148710DC52DCC317EAB611BA214F03BA0FD6862448036658635DBE8AA404E6EBA335C5A44C44345A03E29AC572CD0C4A54BFAB564A1D97DF2E47C2B85A3EAFCC0ED3C4D1BEA2D69C3B61D518BF3C40388B1CCCF079EEC45A346B7813C624BEDAB87D46D43DF89AA835287D077B168856736D47D19E7F0B4E128C859477E80FEC391596874F2F2E96C4BAEFA2FD3A7FC47C00B6157089";

   private void test(int rgb, String s256, String s65536, String title)
   {
      try {it = new ImageTester("etc/images/ImageTest"+title+".bmp");} catch (ImageException e) {fail(e.getMessage());} catch (IOException e) {fail(e.getMessage());}
      assert(s256, s65536, title);
   }

   String []imageNames = // just to let deployer include them automagically
   {
         "etc/images/ImageTest_30x30x8.bmp","etc/images/ImageTest_30x30x4.bmp","etc/images/ImageTest_30x30x2.bmp",
         "etc/images/ImageTest_31x31x8.bmp","etc/images/ImageTest_31x31x4.bmp","etc/images/ImageTest_31x32x2.bmp",
         "etc/images/ImageTest_32x32x8.bmp","etc/images/ImageTest_32x32x4.bmp","etc/images/ImageTest_32x32x2.bmp",
         "etc/images/ImageTest_33x33x8.bmp","etc/images/ImageTest_33x33x4.bmp","etc/images/ImageTest_33x33x2.bmp",
   };

   public void testRun()
   {
      //recording = true;
      //learning = true;
      super.testRun(); // important!

      Settings.showDesktopMessages = false;
      test(0xEEEEEE, _30x30x8_256, _30x30x8_65536, "_30x30x8");
      test(0xFFCCCC, _30x30x4_256, _30x30x4_65536, "_30x30x4");
      test(0xFFFFFF, _30x30x2_256, _30x30x2_65536, "_30x30x2");

      test(0xCCFFFF, _31x31x8_256, _31x31x8_65536, "_31x31x8");
      test(0xDDDDDD, _31x31x4_256, _31x31x4_65536, "_31x31x4");
      test(0xDDDDDD, _31x31x2_256, _31x31x2_65536, "_31x31x2");

      test(0xEEEEEE, _32x32x8_256, _32x32x8_65536, "_32x32x8");
      test(0xDDDDDD, _32x32x4_256, _32x32x4_65536, "_32x32x4");
      test(0xFFFFFF, _32x32x2_256, _32x32x2_65536, "_32x32x2");

      test(0xEEEEEE, _33x33x8_256, _33x33x8_65536, "_33x33x8");
      test(0xDDDDDD, _33x33x4_256, _33x33x4_65536, "_33x33x4");
      test(0xFFFFFF, _33x33x2_256, _33x33x2_65536, "_33x33x2");
   }
}
