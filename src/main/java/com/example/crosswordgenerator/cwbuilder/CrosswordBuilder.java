package com.example.crosswordgenerator.cwbuilder;

import java.util.*;
/**
 * Класс генератор для кроссвордов
 * */
public class CrosswordBuilder {
    // Словарь [символ : словарь [слово : список позиций в слове, где встречается эта буква]]
    private Map<Character, Map<Integer, List<Integer>>> links;
    // Словарь [номер слова в списке words : список пощиций в слове, буквы в которых можно использовать как links]
    private Map<Integer, Word> linksInWords;
    // Списрок слов, которые ещё не были включены в кроссворд
    private List<Word> availableWords;
    // Список букв, которые можно использовать для связи слов в кроссворде
    private List<CharLink> availableLinks;
    // Список слов, которые уже находятся в кроссворде
    private List<PlacedWord> placedWords;

    public CrosswordBuilder(){
        links = new HashMap<>();
        linksInWords = new HashMap<>();
    }

    private void evaluateWords(List<String> words){
        for(int k = 0; k < words.size(); k++){
            String word = words.get(k);
            for(int i=0;i<word.length();i++){
                char ch = word.charAt(i);
                if (links.containsKey(ch)){
                    links.get(ch).merge(k, new LinkedList<>(Arrays.asList(i)), (a, b) -> {
                        a.addAll(b);
                        return a;
                    });
                }
                else {
                    Map<Integer, List<Integer>> wordsForLink = new HashMap<>();
                    wordsForLink.put(k , new LinkedList<>(Arrays.asList(i)));
                    links.put(ch, wordsForLink);
                }
            }
        }
        Iterator<Map.Entry<Character, Map<Integer, List<Integer>>>> iterator = links.entrySet().iterator();
        while(iterator.hasNext()){
            var entry = iterator.next();
            if(entry.getValue().size() > 1){
                for(int wi:entry.getValue().keySet()){
                    for(int inWordPos: entry.getValue().get(wi)){
                        if(linksInWords.containsKey(wi)){
                            linksInWords.get(wi).addCharLink(new CharLink(entry.getKey(), inWordPos, entry.getValue().size(), wi));
                        }
                        else{
                            linksInWords.put(wi, new Word(new CharLink(entry.getKey(), inWordPos, entry.getValue().size(), wi), wi,
                                    words.get(wi).length()));
                        }
                    }
                }
            }else{
                iterator.remove();
            }
        }
    }

    public CrosswordResult buildCrosswordOutOfWords(List<String> words){
        evaluateWords(words);
        List<String> omittedWords = new LinkedList<>();
        List<String> usedWords = new LinkedList<>();
        availableWords = new LinkedList<>();
        for(int i=0;i<words.size();i++){
            if(linksInWords.keySet().contains(i)){
                Word w = linksInWords.get(i);
                availableWords.add(w);
                usedWords.add(words.get(i));
            }else
                omittedWords.add(words.get(i));
        }

        if(availableWords.size() == 0){
            return new CrosswordResult();
        }
        availableLinks = new ArrayList<>();
        placedWords = new LinkedList<>();
        Collections.shuffle(availableWords);
        Word selectedWord = availableWords.get(0);
        PlacedWord pw = new PlacedWord(selectedWord.getLength(), 1000, 1000, selectedWord.getWi(), (Math.random() * 10 % 2 == 0));
        placeWord(selectedWord, pw);
        availableWords.remove(selectedWord);
        boolean result = generateCW();
        if(result){
            int minX=Integer.MAX_VALUE, maxX = 0, minY=Integer.MAX_VALUE, maxY = 0;
            for(PlacedWord pWord: placedWords){
                int wx = pWord.getPosX(), wy = pWord.getPosY(), length = pWord.getLength();
                boolean isHorizontal = pWord.isHorizontal();
                if(wx < minX)
                    minX = wx;
                if(wx + (isHorizontal? length: 0) > maxX)
                    maxX = wx + length;
                if(wy < minY)
                    minY = wy;
                if(wy + (!isHorizontal?length:0) > maxY)
                    maxY = wy + length;
            }
            int width = maxX - minX + 1, height = maxY - minY + 1, offsetX=1, offsetY=1;
            List<WordReadyToSave> wordsReadyToSave = new ArrayList<>();
            int wordindex = 1;
            for(PlacedWord pWord:placedWords){
                wordsReadyToSave.add(new WordReadyToSave(words.get(pWord.getWi()), pWord.isHorizontal(),
                        pWord.getPosX() - minX, pWord.getPosY() - minY));
            }
            return new CrosswordResult(omittedWords,  wordsReadyToSave, width, height);
        }else{
            return new CrosswordResult(omittedWords, null, 0, 0);
        }
    }

    private boolean generateCW(){
        if(availableWords.size() == 0)
            return true;
        for(int charLinkPos = 0;charLinkPos < availableLinks.size(); charLinkPos++){
            availableLinks.sort(Comparator.comparingInt(CharLink::getPrevalence));
            CharLink selectedCharLink = availableLinks.get(charLinkPos);
            int linkX = selectedCharLink.getPosX(), linkY = selectedCharLink.getPosY();
            boolean isHorizontal = selectedCharLink.isInHorizontalWord();
            for(int wordPos = 0; wordPos < availableWords.size(); wordPos++){
                Word selectedWord = availableWords.get(wordPos);
                if(!links.get(selectedCharLink.getCharacter()).containsKey(selectedWord.getWi()))
                    continue;
                List<Integer> availablePositions = links.get(selectedCharLink.getCharacter()).get(selectedWord.getWi());
                Collections.shuffle(availablePositions);
                for(int linkInWord = 0; linkInWord < availablePositions.size(); linkInWord++){
                    int wordX, wordY;
                    if(isHorizontal){
                        wordX = linkX;
                        wordY = linkY - availablePositions.get(linkInWord);
                    }else{
                        wordY = linkY;
                        wordX = linkX - availablePositions.get(linkInWord);
                    }
                    boolean collide = false;
                    for(PlacedWord pw:placedWords)
                        if(pw.getWi() != selectedCharLink.getWi() && pw.checkCollision(selectedWord.getLength(),
                                wordX, wordY, !isHorizontal)){
                            collide = true;
                            break;
                        }
                    if(!collide){
                        PlacedWord genPlacedWord = new PlacedWord(selectedWord.getLength(), wordX, wordY, selectedWord.getWi(), !isHorizontal);
                        placeWord(selectedWord, genPlacedWord);
                        availableWords.remove(selectedWord);
                        availableLinks.remove(selectedCharLink);
                        boolean wordsPlaced = generateCW();
                        if(wordsPlaced)
                            return true;
                        placedWords.remove(genPlacedWord);
                        availableWords.add(wordPos, selectedWord);
                        for(CharLink cl: selectedWord.getAll()) {
                            availableLinks.remove(cl);
                            System.out.println("Remove charlink " + cl);
                        }
                        availableLinks.add(selectedCharLink);
                    }
                }
            }
        }
        return false;
    }

    private void placeWord(Word word, PlacedWord pw){
        placedWords.add(pw);
        for(CharLink cl: word.getAll()){
            CharLink placedLink = new CharLink(cl);
            placedLink.setPosX(pw.isHorizontal()?pw.getPosX() +  cl.getPosition():pw.getPosX());
            placedLink.setPosY(!pw.isHorizontal()?pw.getPosY() + cl.getPosition(): pw.getPosY());
            placedLink.setInHorizontalWord(pw.isHorizontal());
            availableLinks.add(placedLink);
        }
    }
}
